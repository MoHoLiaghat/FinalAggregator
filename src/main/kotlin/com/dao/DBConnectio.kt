import com.config.Config
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

object DBConnectio {
    val ds = HikariDataSource()
   // var con = ds.getConnection()
    init {
        ds.setMaximumPoolSize(50)
        ds.setDriverClassName(Config.Driver)
        ds.setJdbcUrl(Config.JdbcUrl)
        ds.setUsername(Config.Username)
        ds.setPassword(Config.Password)
        try{
            //con = ds.getConnection()
            //con.close()
        } catch(e: SQLException){
            e.printStackTrace()
        }
    }

    fun getConnection(): Connection {
        var con = ds.getConnection()
        try{
            //con = ds.getConnection()
        } catch(e: SQLException){
            e.printStackTrace()
        }
        return con
    }
}