package com.config

/**
 * @author Reza Varmazyari , Mohammad hossein Liaghat
 *
 */
object Config {
    const val jdbcUrl = "jdbc:mysql://localhost:3306/aggregator"
    const val username = "root"
    const val password = "123"
    const val driver = "com.mysql.jdbc.Driver"
    const val bootstrapServers = "192.168.1.51:9092"
    var groupId = "Group_id_38"
    const val enableAutoCommit = false
    const val autoCommitIntervalMs = 1000
    const val keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val autoOffsetReset = "earliest"
    const val maxPollRecords = 10000
    const val subscribtion =  "rahkar_test"
    const val maxPollIntervalMs = Integer.MAX_VALUE
    const val readFromBeginning = true
    const val databaseConnectionTimeout:Long = 64000
    const val fetchMessageMaxBytes = 1048576

}