package ir.sls.aggregator.service

import ir.sls.aggregator.config.Config
import ir.sls.aggregator.dao.NormalizedUrlDao
import ir.sls.aggregator.dao.OriginalUrlDao
import ir.sls.aggregator.model.DataRecord
import ir.sls.aggregator.model.DataStore
import mu.KotlinLogging
import java.sql.SQLException


object DatabaseService {

private val logger = KotlinLogging.logger{}

    /**
     * persisting a hashmap of dataRecords into database
     * @param  hashmap of dataRecords
     *@exception <SQLException>.
     * @author Reza Varmazyari
     */

    var jdbcUrl = Config.jdbcUrl
    var username = Config.username
    var password = Config.password
    var driver = Config.driver


    fun setProperties(jdbcUrl2:String ,username2:String, password2:String,driver2:String  ){
        jdbcUrl = jdbcUrl2
        username = username2
        password = password2
        driver = driver2
    }


    fun save(heap: HashMap<String, DataRecord>):Boolean {


        var allSaveSuccess = false
        val con = DBConnection.getConnection(driver, jdbcUrl, username, password)
        try {
            con?.autoCommit = false
            NormalizedUrlDao.setConnection(con)
            heap.forEach {
                NormalizedUrlDao.add(it.value)
            }
            NormalizedUrlDao.flush()
            OriginalUrlDao.setConnection(con)
            heap.forEach {
                OriginalUrlDao.add(it.value)
            }
            OriginalUrlDao.flush()
            con?.commit()
            allSaveSuccess = true
        } catch (e: KotlinNullPointerException){
            logger.error (e){ "DB Connection Error" }
            allSaveSuccess = false
        } catch (e: SQLException){
            con?.rollback()
            logger.error (e){ "DB Write Error" }
            allSaveSuccess = false
        } finally {
            con?.close()
        }
        return if(allSaveSuccess){
            DataStore.recordsArray.removeAll(DataStore.recordsArray)
            true
        }
        else
            false
    }

}