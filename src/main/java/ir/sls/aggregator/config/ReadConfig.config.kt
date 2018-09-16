package ir.sls.aggregator.config
import com.typesafe.config.ConfigFactory
import java.io.File
import com.google.gson.Gson
import com.typesafe.config.ConfigRenderOptions

/**
 * @author Mohammad Hossein Liaghat Email: mohamadliaghat@gmail.com
 * The data class in order to figure out the kafka
 */

data class Config(val DataBase: DataBase, val Kafka: Kafka)

data class DataBase(
        val jdbcUrl: String,
        val username: String,
        val password: String,
        val driver: String,
        val maximumPoolSize: Int,
        val databaseConnectionTimeout: Long,
        val databaseConnectionMaxTimeout: Long
)

data class Kafka(
        val bootstrapServers: List<String>,
        val groupId: String,
        val enableAutoCommit: Boolean,
        val autoCommitIntervalMs: Int,
        val keyDeserializer: String,
        val valueDeserializer: String,
        val autoOffsetReset: String,
        val maxPollRecords: Int,
        val subscribtion: String,
        val maxPollIntervalMs: Int,
        val readFromBeginning: Boolean,
        val fetchMessageMaxBytes: Int
)


fun main(args: Array<String>)
{


}
