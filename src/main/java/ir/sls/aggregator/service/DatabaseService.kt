package ir.sls.aggregator.service

import ir.sls.aggregator.config.ReadConfig
import ir.sls.aggregator.dao.Dao
import ir.sls.aggregator.dao.NormalizedUrlDao
import ir.sls.aggregator.dao.OriginalUrlDao
import ir.sls.aggregator.metric.InitMeter
import ir.sls.aggregator.model.DataRecord
import mu.KotlinLogging
import java.sql.SQLException


/**
 * persisting a hashmap of dataRecords into database
 * @param  HashMap of dataRecords
 * @exception <SQLException>.
 * @author Reza Varmazyari
 */

class DatabaseService : Dao<String, DataRecord>()
{

    private val logger = KotlinLogging.logger {}


    var jdbcUrl = ReadConfig.config.dataBase.jdbcUrl
    var username = ReadConfig.config.dataBase.username
    var password = ReadConfig.config.dataBase.password
    var driver = ReadConfig.config.dataBase.driver

    init
    {
        DBConnection.setProperties(driver, jdbcUrl, username, password)
    }

    fun setProperties(jdbcUrl2: String, username2: String, password2: String, driver2: String)
    {
        jdbcUrl = jdbcUrl2
        username = username2
        password = password2
        driver = driver2
        DBConnection.setProperties(driver, jdbcUrl, username, password)
    }


    override fun processData(heap: HashMap<String, DataRecord>): Boolean
    {


        var allSaveSuccess = false

        var con = DBConnection.getConnection()
        while (con == null)
        {
            var timeOut: Long = ReadConfig.config.dataBase.databaseConnectionTimeout
            con = DBConnection.getConnection()
            timeOut *= 2
            if (timeOut == ReadConfig.config.dataBase.databaseConnectionMaxTimeout)
                timeOut = 1000
            Thread.sleep(timeOut)
        }
        try
        {
            con?.autoCommit = false
            NormalizedUrlDao.setConnection(con)
            NormalizedUrlDao.persist(heap)
            OriginalUrlDao.setConnection(con)
            OriginalUrlDao.persist(heap)
            con?.commit()
            InitMeter.markDatabaseWrite(heap.size.toLong())
            allSaveSuccess = true
        }
        catch (e: KotlinNullPointerException)
        {
            logger.error(e) { "Failed to create connection to database" }
        }
        catch (e: SQLException)
        {
            con?.rollback()
            logger.error(e) { "Failed to write in database" }
        }
        finally
        {
            con?.close()
        }
        return if (allSaveSuccess)
        {
            true
        }
        else
            false
    }

}
