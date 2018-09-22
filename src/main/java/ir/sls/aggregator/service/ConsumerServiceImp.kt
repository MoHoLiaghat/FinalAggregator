package ir.sls.aggregator.service

import ir.sls.aggregator.config.Config
import ir.sls.aggregator.config.KafkaFactory
import ir.sls.aggregator.config.ReadConfig
import ir.sls.aggregator.metric.InitMeter
import ir.sls.aggregator.model.DataRecord
import ir.sls.aggregator.util.GsonReader
import kafka.common.KafkaException
import mu.KLogger
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

/**
 * gathering date and aggregate  then persist in Database (MySql)
 * @return persist in database
 * @exception <kafkaException>
 * @author Aryan Gholamlou , Reza Varmazyari , Email : Aryan.gholamlou@gmail.com ,  the.alxan@gmail.com
 */

class ConsumerServiceImp{
    private val logger:KLogger = KotlinLogging.logger {}

    fun aggregateAndPersist(recordsArray:ArrayList<DataRecord>):Boolean{
        logger.info("Got ${DataStore.recordsArray.size} records")
        val heap: HashMap<String, DataRecord> = AggregatorService.aggregate(recordsArray)
        val t1 = Date().time
        val saveSuccess = DatabaseService.save(heap)
        logger.info("Saved :: $saveSuccess")
        val t2 = Date().time
        logger.info("Time :: " + (t2 - t1))
        return saveSuccess
    }

    fun start() {
        metricService()

        var saveSuccess = true
        val consumer = KafkaFactory.createKafkaConsumer() ?: throw IllegalStateException()
        consumer?.subscribe(arrayListOf(ReadConfig.config.kafka.subscription))

        while (true) {
            var records: ConsumerRecords<String, String> = ConsumerRecords.empty()
            if (saveSuccess) {
                try {

                    records = consumer!!.poll(Duration.ofMinutes(1))
                    InitMeter.markKafkaRead(records.count().toLong())

                } catch (e: KafkaException) {
                    logger.error(e) { "Kafka Error" }
                }
            }
            for (record in records) {
                val dataRecord: DataRecord = GsonReader().fromJson(record.value(), DataRecord::class.java)
                DataStore.recordsArray.add(dataRecord)
                if (DataStore.recordsArray.size == 1)
                    logger.info("Partition :: ${record.partition()} , Offset :: ${record.offset()}")
            }


            if (DataStore.recordsArray.size > 0) {
                saveSuccess = aggregateAndPersist(DataStore.recordsArray)
                if (saveSuccess) {
                    consumer?.commitSync()
                }
            }

        }
    }


}