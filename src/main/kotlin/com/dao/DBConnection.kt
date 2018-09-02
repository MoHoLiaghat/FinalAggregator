import com.config.Config
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

object DBConnection {
    val ds = HikariDataSource()
    init {
        ds.setMaximumPoolSize(50)
        ds.setDriverClassName(Config.Driver)
        ds.setJdbcUrl(Config.JdbcUrl)
        ds.setUsername(Config.Username)
        ds.setPassword(Config.Password)

    }

    fun getConnection(): Connection? {
        return try{
            ds.getConnection()
        } catch(e: SQLException){
            e.printStackTrace()
            null
        }
    }
}