package com.service

import com.config.Config
import com.google.gson.Gson
import com.model.DataRecord
import com.model.DataStore
import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import java.util.*

object ConsumerService {
    private val logger = KotlinLogging.logger {}

    /**
     * gathering date and aggregate and persist
     * @return persist in database
     * @exception <kafkaException>
     * @author Aryan Gholamlou , Reza Varmazyari
     */

    fun consume() {
        logger.info { "test" }
        val gson = Gson()

        val consumer = KafkaService.getKafkaConsumer()
        consumer?.subscribe(Arrays.asList(Config.Subscribtion))
        var counterLog = 0
        var total = 0
        var recordsSaved = true
        while (true) {
            if (recordsSaved) {
                recordsSaved = false
                var records: ConsumerRecords<String, String> = ConsumerRecords.empty()

                try {
                    records = consumer!!.poll(Duration.ofNanos(1))

                } catch (e: KafkaException) {
                }
                for (record in records) {
                    val dataRecord: DataRecord = gson.fromJson(record.value(), DataRecord::class.java)
                    DataStore.recordsArray.add(dataRecord)
                    if (DataStore.recordsArray.size == 1)
                        logger.info("Offset :: " + record.offset())
                    counterLog++
                }


                if (DataStore.recordsArray.size > 0) {
                    logger.info("Got " + DataStore.recordsArray.size + " records")
                    total += counterLog
                    var Heap: HashMap<String, DataRecord> = AggregatorService.aggregate(DataStore.recordsArray)
                    var t1 = Date().time
                    DatabaseService.save(Heap)
                    var t2 = Date().time
                    logger.info("Time :: " + (t2 - t1))

                    consumer?.commitSync()

                }
            }
            if (DataStore.recordsArray.size == 0)
                recordsSaved = true

        }
    }
}