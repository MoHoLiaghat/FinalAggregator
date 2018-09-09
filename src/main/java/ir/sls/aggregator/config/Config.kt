package ir.sls.aggregator.config

/**
 * @author Reza Varmazyari
 * @author Mohammad hossein Liaghat
 *  @property  "bootstrap.servers"           a list of host/port pairs to use for establishing the initial connection to the Kafka cluster.
 *  @property   "group.id"                  a unique string that identifies the consumer group this consumer belongs to.
 *  @property   "enable.auto.commit"        if true the consumer's offset will be periodically committed in the background.
 *  @property    "auto.commit.interval.ms"    the frequency in milliseconds that the consumer offsets are auto-committed to Kafka
 *  @property   "key.deserializer"          Deserializer class for key that implements the org.apache.kafka.common.serialization.Deserializer interface.
 *  @property   "value.deserializer"        Deserializer class for value that implements the org.apache.kafka.common.serialization.Deserializer interface.
 *  @property   "auto.offset.reset"         what to do when there is no initial offset in Kafka or if the current offset does not exist any more on the server
 *  @property   "max.poll.records"          the maximum number of records returned in a single call to poll().
 *  @property   "max.poll.interval.ms"      the maximum delay between invocations of poll() when using consumer group management
 *  @property   "fetch.message.max.bytes"   the number of bytes of messages to attempt to fetch for each topic-partition in each fetch request.
 */
object Config {

    object DataBase {
        const val jdbcUrl = "jdbc:mysql://localhost:3306/aggregator"
        const val username = "root"
        const val password = "123"
        const val driver = "com.mysql.jdbc.Driver"
        const val maximumPoolSize = 50
        const val databaseConnectionTimeout = 1000L
        const val databaseConnectionMaxTimeout:Long = 64000L
    }
   object Kafka {
       const val bootstrapServers = "192.168.1.53:9092"
       var groupId = "Group_id_44"
       const val enableAutoCommit = false
       const val autoCommitIntervalMs = 1000
       const val keyDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
       const val valueDeserializer = "org.apache.kafka.common.serialization.StringDeserializer"
       const val autoOffsetReset = "earliest"
       const val maxPollRecords = 10000
       const val subscribtion =  "rahkar_test"
       const val maxPollIntervalMs = Integer.MAX_VALUE
       const val readFromBeginning = true
       const val fetchMessageMaxBytes = 1048576
   }


}
