package ir.sls.aggregator.service

import ir.sls.aggregator.config.Config
import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

// Amin: Rename class to KafkaFactory
object KafkaFactory {
    private val logger = KotlinLogging.logger{}

    // Amin: put properties documentation in Config object.
    /**
     *  Instanting a kafka consumer
     *   prepare a kafka consumer
     *  @return a Kafka consumer , map of Strings
     *  @author Aryan Gholamlou , Mohammad hossein Liaghat
     *  @exception <RuntimeException>

     */
    // Amin: It is better to rename this method to createKafkaConsumer.
    fun createKafkaConsumer(): KafkaConsumer<String, String>? {
        val props = Properties()
        props["bootstrap.servers"] = Config.Kafka.bootstrapServers
        props["group.id"] = Config.Kafka.groupId
        props["enable.auto.commit"] = Config.Kafka.enableAutoCommit
        props["auto.commit.interval.ms"] = Config.Kafka.autoCommitIntervalMs
        props["key.deserializer"] = Config.Kafka.keyDeserializer
        props["value.deserializer"] = Config.Kafka.valueDeserializer
        props["auto.offset.reset"] = Config.Kafka.autoOffsetReset
        props["max.poll.records"] = Config.Kafka.maxPollRecords
        props["max.poll.interval.ms"] = Config.Kafka.maxPollIntervalMs
        props["fetch.message.max.bytes"] = Config.Kafka.fetchMessageMaxBytes

        var consumer:KafkaConsumer<String, String>? = null
        try {
            consumer = KafkaConsumer(props)
        } catch (e: KafkaException) {
            // Amin: better message -> Cannot create Kafka consumer.
            // Amin: If Kafka is down, Aggregator should not run
            logger.error(e) { "Cannot create Kafka consumer" }
        }
        // Amin: This documentation should be before method.

        return consumer
    }
}