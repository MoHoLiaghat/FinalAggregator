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
        // Amin: DataSource should be configured in init{} block not on every getConnection
        ds.dataSource.connection
        ds.maximumPoolSize = Config.maximumPoolSize
        ds.driverClassName = driver
        ds.jdbcUrl = jdbcUrl
        ds.username = username
        ds.password = password
        // Amin: It is better not to return null on exception but throw it and catch where you
        // want to reconnect.
        //Amin: use safe call operator (?.) less
        return try{
            ds.connection
        } catch (e: ConnectException){
            // Amin: messages should be a statement.
            logger.error(e) { "DB Connection Error" }
            null

        }catch (e: CommunicationsException){
            logger.error(e) { "DB not available" }
            timeOut *= 2
            // Amin: timeout should be greater than config to reach the config value
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