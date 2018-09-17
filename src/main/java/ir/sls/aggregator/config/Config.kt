package ir.sls.aggregator.config

/**
 * @author Mohammad Hossein Liaghat Email: mohamadliaghat@gmail.com
 * Read config data from hocon file ==> -Dconfig.file=src/main/resources/aggregator_config.hocon
 * The data class in order to figure out the kafka
 */

data class Config(val dataBase: DataBase, val kafka: Kafka, val spark: Spark)

data class DataBase(
        val jdbcUrl: String = "jdbc:mysql://localhost:3306/aggregator",
        val username: String = "root",
        val password: String ="123",
        val driver: String = "com.mysql.jdbc.Driver",
        val maximumPoolSize: Int = 50,
        val databaseConnectionTimeout: Long = 1000L,
        val databaseConnectionMaxTimeout: Long = 64000L
)

data class Kafka(
        val bootstrapServers: List<String> = arrayListOf("192.168.1.53:9092"),
        val groupId: String = "Group_id_44",
        val enableAutoCommit: Boolean = false,
        val autoCommitIntervalMs: Int = 1000,
        val keyDeserializer: String = "org.apache.kafka.common.serialization.StringDeserializer",
        val valueDeserializer: String = "org.apache.kafka.common.serialization.StringDeserializer",
        val autoOffsetReset: String = "earliest",
        val maxPollRecords: Int = 10000,
        val subscription: String = "rahkar_test",
        val maxPollIntervalMs: Int = Integer.MAX_VALUE,
        val readFromBeginning: Boolean = true,
        val fetchMessageMaxBytes: Int = 1048576
)

data class Spark(val port: Int)
