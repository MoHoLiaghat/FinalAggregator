package com.service

import com.config.Config
import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

object KafkaService {

    private val logger = KotlinLogging.logger{}


    /**
     *  Instanting a kafka consumer
     *  @author Aryan Gholamlou , Mohammad hossein Liaghat
     *  @exception <RuntimeException>
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
     *
     */

    fun getKafkaConsumer(): KafkaConsumer<String, String>? {
        val props = Properties()
        props["bootstrap.servers"] = Config.bootstrapServers
        props["group.id"] = Config.groupId
        props["enable.auto.commit"] = Config.enableAutoCommit
        props["auto.commit.interval.ms"] = Config.autoCommitIntervalMs
        props["key.deserializer"] = Config.keyDeserializer
        props["value.deserializer"] = Config.valueDeserializer
        props["auto.offset.reset"] = Config.autoOffsetReset
        props["max.poll.records"] = Config.maxPollRecords
        props["max.poll.interval.ms"] = Config.maxPollIntervalMs
        props["fetch.message.max.bytes"] = Config.fetchMessageMaxBytes

        var consumer:KafkaConsumer<String, String>? = null
        try {
            consumer = KafkaConsumer<String, String>(props)
        } catch (e: KafkaException) {
            logger.error(e) { "kafka consumer error" }
        }
        /**
         *  prepare a kafka consumer
         *  @return a Kafka consumer , map of Strings
         */
        return consumer
    }
}