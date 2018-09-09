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
    private val ds = HikariDataSource()

    fun setProperties(driver:String,jdbcUrl:String,username:String,password:String) {
        ds.maximumPoolSize = Config.DataBase.maximumPoolSize
        ds.driverClassName = driver
        ds.jdbcUrl = jdbcUrl
        ds.username = username
        ds.password = password
    }

    fun getConnection(): Connection? {
        return try{
            ds.connection
        } catch (e: ConnectException){
            logger.error(e) { "Failded to create connection to database" }
            null

        }catch (e: CommunicationsException){
            logger.error(e) { "Failed to communicate with database" }
            null

        } catch(e: SQLException){
            logger.error (e){"Failed to write in database"}
            null
        }
    }
}