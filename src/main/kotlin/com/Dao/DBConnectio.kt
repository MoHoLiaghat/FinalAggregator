import com.Config.Config
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

object DBConnectio {
    val ds = HikariDataSource()

    init {
        ds.setMaximumPoolSize(50)
        ds.setDriverClassName(Config.Driver)
        ds.setJdbcUrl(Config.JdbcUrl)
        ds.setUsername(Config.Username)
        ds.setPassword(Config.Password)
   //     ds.addDataSourceProperty("cachePrepStmts", "true")
     //   ds.addDataSourceProperty("prepStmtCacheSize", "250")
    //    ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
   //     ds.addDataSourceProperty("useServerPrepStmts", "true")
    }

    fun getConnection(): Connection {

        return ds.getConnection()
    }
}