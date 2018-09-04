import ir.sls.aggregator.config.Config
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException
import com.zaxxer.hikari.HikariDataSource
import mu.KotlinLogging
import java.net.ConnectException
import java.sql.Connection
import java.sql.SQLException

/**
 * @author Reza Varmazyari
 */
object DBConnection {
    private val logger = KotlinLogging.logger{}
    private var timeOut:Long = Config.databaseConnectionTimeout
    private val ds = HikariDataSource()

    fun getConnection(driver:String,jdbcUrl:String,username:String,password:String): Connection? {
        ds.maximumPoolSize = Config.maximumPoolSize
        ds.driverClassName = driver
        ds.jdbcUrl = jdbcUrl
        ds.username = username
        ds.password = password
        return try{
            ds.connection
        } catch (e: ConnectException){
            logger.error(e) { "DB Connection Error" }
            null

        }catch (e: CommunicationsException){
            logger.error(e) { "DB not available" }
            timeOut *= 2
            if (timeOut == Config.databaseConnectionMaxTimeout)
                timeOut = 1000
            Thread.sleep(timeOut)
            null

        } catch(e: SQLException){
            logger.error (e){"DB Write Error"}
            null
        }
    }
}