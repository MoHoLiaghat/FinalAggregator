package ir.sls.aggregator.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

abstract class ConsumerService{
    val logger:KLogger = KotlinLogging.logger {}

    abstract fun processData(recordsArray: ArrayList<DataRecord>):Boolean
    inline fun <reified T> Gson.customFromJson(json:String) = this.fromJson<T>(json,object: TypeToken<T>(){}.type)

    fun start() {
        metricService()

        var saveSuccess = true
        val consumer = KafkaFactory.createKafkaConsumer() ?: throw IllegalStateException()
        consumer?.subscribe(arrayListOf(ReadConfig.config.kafka.subscription))

        while (true) {
            var records: ConsumerRecords<String, String> = ConsumerRecords.empty()
            var recordsArray:ArrayList<DataRecord> = ArrayList()
            if (saveSuccess) {
                try {
                    records = consumer!!.poll(Duration.ofMinutes(1))
                    InitMeter.markKafkaRead(records.count().toLong())
                } catch (e: KafkaException) {
                    logger.error(e) { "Kafka Error" }
                }
            }
            for (record in records) {
                val dataRecord: DataRecord = GsonReader().customFromJson<DataRecord>(record.value())
                recordsArray.add(dataRecord)
                if (recordsArray.size == 1)
                    logger.info("Partition :: ${record.partition()} , Offset :: ${record.offset()}")
            }
            if (recordsArray.size > 0) {
                saveSuccess = processData(recordsArray)
                if (saveSuccess) {
                    consumer?.commitSync()
                }
            }

        }
    }


}