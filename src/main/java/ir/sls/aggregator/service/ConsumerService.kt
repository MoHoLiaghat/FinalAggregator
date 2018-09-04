package ir.sls.aggregator.service

import ir.sls.aggregator.config.Config
import com.google.gson.Gson
import ir.sls.aggregator.model.DataRecord
import ir.sls.aggregator.model.DataStore
import kafka.common.KafkaException
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList

object ConsumerService {
    private val logger = KotlinLogging.logger {}

    /**
     * gathering date and aggregate  then persist in Database (MySql)
     * @return persist in database
     * @exception <kafkaException>
     * @author Aryan Gholamlou , Reza Varmazyari , Email : Aryan.gholamlou@gmail.com ,  the.alxan@gmail.com
     */

    fun aggregateAndPersist(recordsArray:ArrayList<DataRecord>):Boolean{
        logger.info("Got ${DataStore.recordsArray.size} records")
        var heap: HashMap<String, DataRecord> = AggregatorService.aggregate(recordsArray)
        var t1 = Date().time
        val saveSuccess = DatabaseService.save(heap)
        logger.info("Saved :: $saveSuccess")
        var t2 = Date().time
        logger.info("Time :: " + (t2 - t1))
        return saveSuccess
    }

    fun consume() {
        val gson = Gson().newBuilder().create()
        var saveSuccess = true
        val consumer = KafkaService.getKafkaConsumer()
        consumer?.subscribe(Arrays.asList(Config.subscribtion))
        if(Config.readFromBeginning)
            consumer?.seekToBeginning(emptyList())

        while (true) {

            var records: ConsumerRecords<String, String> = ConsumerRecords.empty()
            if (saveSuccess) {
                try {
                    records = consumer!!.poll(Duration.ofMinutes(1))

                } catch (e: KafkaException) {
                    logger.error(e) { "Kafka Error" }
                }
            }
            for (record in records) {
                val dataRecord: DataRecord = gson.fromJson(record.value(), DataRecord::class.java)
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