package ir.sls.aggregator.config

import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

// Amin: Rename class to KafkaFactory
object KafkaFactory
{
    private val logger = KotlinLogging.logger {}

    // Amin: put properties documentation in Config object.
    /**
     *  Instanting a kafka consumer
     *   prepare a kafka consumer
     *  @return a Kafka consumer , map of Strings
     *  @author Aryan Gholamlou , Mohammad hossein Liaghat
     *  @exception <RuntimeException>
     */

    fun createKafkaConsumer(): KafkaConsumer<String, String>?
    {
        val props = Properties()
        props["bootstrap.servers"] = ReadConfig.config.kafka.bootstrapServers
        props["group.id"] = ReadConfig.config.kafka.groupId
        props["enable.auto.commit"] = ReadConfig.config.kafka.enableAutoCommit
        props["auto.commit.interval.ms"] = ReadConfig.config.kafka.autoCommitIntervalMs
        props["key.deserializer"] = ReadConfig.config.kafka.keyDeserializer
        props["value.deserializer"] = ReadConfig.config.kafka.valueDeserializer
        props["auto.offset.reset"] = ReadConfig.config.kafka.autoOffsetReset
        props["max.poll.records"] = ReadConfig.config.kafka.maxPollRecords
        props["max.poll.interval.ms"] = ReadConfig.config.kafka.maxPollIntervalMs
        props["fetch.message.max.bytes"] = ReadConfig.config.kafka.fetchMessageMaxBytes

        var consumer: KafkaConsumer<String, String>? = null
        try
        {
            consumer = KafkaConsumer(props)
        }
        catch (e: KafkaException)
        {
            logger.error(e) { "Cannot create Kafka consumer" }
        }

        return consumer
    }
}