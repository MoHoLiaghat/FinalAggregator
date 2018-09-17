package ir.sls.aggregator.config

/**
 * @author Mohammad Hossein Liaghat Email: mohamadliaghat@gmail.com
 * Read config data from hocon file ==> -Dconfig.file=src/main/resources/aggregator_config.hocon
 * The data class in order to figure out the kafka
 */

data class Config(val dataBase: DataBase, val kafka: Kafka, val spark: Spark)

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
        val subscription: String,
        val maxPollIntervalMs: Int,
        val readFromBeginning: Boolean,
        val fetchMessageMaxBytes: Int
)

data class Spark(val port: Int)