package ir.sls.aggregator.config

/**
 * @author Reza Varmazyari
 * @author Mohammad hossein Liaghat
 */
object Config {
    // Amin: group related configs such as Database config, Kafka configs...
    const val jdbcUrl = "jdbc:mysql://localhost:3306/aggregator"
    const val username = "root"
    const val password = "123"
    const val driver = "com.mysql.jdbc.Driver"
    const val bootstrapServers = "192.168.1.51:9092"
    var groupId = "Group_id_42"
    const val enableAutoCommit = false
    const val autoCommitIntervalMs = 1000
    const val keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val autoOffsetReset = "earliest"
    const val maxPollRecords = 10000
    const val subscribtion =  "rahkar_test"
    const val maxPollIntervalMs = Integer.MAX_VALUE
    const val readFromBeginning = true
    const val databaseConnectionMaxTimeout:Long = 64000L
    const val fetchMessageMaxBytes = 1048576
    const val maximumPoolSize = 50
    const val databaseConnectionTimeout = 1000L

}
