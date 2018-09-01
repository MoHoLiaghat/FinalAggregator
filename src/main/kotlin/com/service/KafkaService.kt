package com.service

import com.config.Config
import kafka.common.KafkaException
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

object KafkaService {
    /*
    Instantiating a kafkaConsumer
        Input:

        Output:
            a kafkaConsumer
 */
    fun getKafkaConsumer(): KafkaConsumer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = Config.Bootstrap_servers
        props["group.id"] = Config.Group_id
        props["enable.auto.commit"] = Config.Enable_auto_commit
        props["auto.commit.interval.ms"] = Config.Auto_commit_interval_ms
        props["key.deserializer"] = Config.Key_deserializer
        props["value.deserializer"] = Config.Value_deserializer
        props["auto.offset.reset"] = Config.Auto_offset_reset
        props["max.poll.records"] = Config.Max_poll_records
        props["max.poll.interval"] = Config.Max_poll_interval_ms
        val consumer = KafkaConsumer<String, String>(props)
        try {
            //consumer = KafkaConsumer<String, String>(props)
        } catch (e: KafkaException) {
            e.printStackTrace()
        }
        return consumer
    }
}