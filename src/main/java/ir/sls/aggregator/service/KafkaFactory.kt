package ir.sls.aggregator.service

import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

// Amin: Rename class to KafkaFactory
object KafkaFactory
{
    private val logger = KotlinLogging.logger {}

    // Amin: put properties documentation in `ReadConfig.config` object.
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
        props["bootstrap.servers"] = ReadConfig.config.Kafka.bootstrapServers
        props["group.id"] = ReadConfig.config.Kafka.groupId
        props["enable.auto.commit"] = ReadConfig.config.Kafka.enableAutoCommit
        props["auto.commit.interval.ms"] = ReadConfig.config.Kafka.autoCommitIntervalMs
        props["key.deserializer"] = ReadConfig.config.Kafka.keyDeserializer
        props["value.deserializer"] = ReadConfig.config.Kafka.valueDeserializer
        props["auto.offset.reset"] = ReadConfig.config.Kafka.autoOffsetReset
        props["max.poll.records"] = ReadConfig.config.Kafka.maxPollRecords
        props["max.poll.interval.ms"] = ReadConfig.config.Kafka.maxPollIntervalMs
        props["fetch.message.max.bytes"] = ReadConfig.config.Kafka.fetchMessageMaxBytes

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