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
    var timeOut:Long = 1000
    val ds = HikariDataSource()
    init {
        ds.setMaximumPoolSize(50)
        ds.setDriverClassName(Config.driver)
        ds.setJdbcUrl(Config.jdbcUrl)
        ds.setUsername(Config.username)
        ds.setPassword(Config.password)

    }

    fun getConnection(): Connection? {
        return try{
            ds.getConnection()
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