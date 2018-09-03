import com.config.Config
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
    private var timeOut:Long = 1000
    private val ds = HikariDataSource()
    init {
        ds.maximumPoolSize = 50
        ds.driverClassName = Config.driver
        ds.jdbcUrl = Config.jdbcUrl
        ds.username = Config.username
        ds.password = Config.password

    }

    fun getConnection(): Connection? {
        return try{
            ds.connection
        } catch (e: ConnectException){
            logger.error(e) { "DB Connection Error" }
            null

        }catch (e: CommunicationsException){
            logger.error(e) { "DB not available" }
            timeOut *= 2
            if (timeOut == Config.databaseConnectionTimeout)
                timeOut = 2000
            Thread.sleep(timeOut)
            null

        } catch(e: SQLException){
            logger.error (e){"DB Write Error"}
            null
        }
    }
}