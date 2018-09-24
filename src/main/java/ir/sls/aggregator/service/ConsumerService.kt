package ir.sls.aggregator.service

import ir.sls.aggregator.config.KafkaFactory
import ir.sls.aggregator.config.ReadConfig
import ir.sls.aggregator.metric.InitMeter
import ir.sls.aggregator.util.gson
import kafka.common.KafkaException
import mu.KLogger
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import kotlin.collections.ArrayList

/**
 * gathering date and aggregate  then persist in Database (MySql)
 * @return persist in database
 * @exception <kafkaException>
 * @author Aryan Gholamlou , Reza Varmazyari , Email : Aryan.gholamlou@gmail.com ,  the.alxan@gmail.com
 */

abstract class ConsumerService<T>{
    val logger:KLogger = KotlinLogging.logger {}
    private var dataType:Class<T>

    constructor(dataType:Class<T>){
        this.dataType = dataType
    }

    abstract fun processData(recordsArray: ArrayList<T>):Boolean

    fun start() {
        metricService()
        var saveSuccess = true
        val consumer = KafkaFactory.createKafkaConsumer() ?: throw IllegalStateException()
        consumer?.subscribe(arrayListOf(ReadConfig.config.kafka.subscription))
        var records: ConsumerRecords<String, String> = ConsumerRecords.empty()
        while (true) {
            var recordsArray:ArrayList<T> = arrayListOf()
            if (saveSuccess) {
                try {
                    records = consumer!!.poll(Duration.ofMinutes(1))
                    InitMeter.markKafkaRead(records.count().toLong())
                } catch (e: KafkaException) {
                    logger.error(e) { "Kafka Error" }
                }
            }
            records.map { record ->
                val dataRecord: T = gson.fromJson<T>(record.value(),dataType)
                println(dataRecord.toString())
                recordsArray.add(dataRecord)
                if (recordsArray.size == 1)
                    logger.info("Partition :: ${record.partition()} , Offset :: ${record.offset()}")
            }
            if (recordsArray.size > 0) {
                saveSuccess = processData(recordsArray)
                if (saveSuccess) {
                    records.removeAll{true}
                    consumer?.commitSync()
                }
            }

        }
    }


}