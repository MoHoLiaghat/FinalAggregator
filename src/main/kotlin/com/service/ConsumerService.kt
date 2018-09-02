package com.service

import com.config.Config
import com.google.gson.Gson
import com.model.DataRecord
import com.model.DataStore
import kafka.common.KafkaException
import org.apache.kafka.clients.consumer.ConsumerRecords
import java.time.Duration
import java.util.*

object ConsumerService {

/*

  Gathering date and aggregate and persist
    Input: null
    Action:
    Output: persist in mysql


 */

    fun consume() {
        val gson = Gson()
        val consumer = KafkaService.getKafkaConsumer()
        val consumer2 = KafkaService.getKafkaConsumer()
        consumer?.subscribe(Arrays.asList(Config.Subscribtion))
        consumer2?.subscribe(Arrays.asList(Config.Subscribtion))
        var c = 0
        var j =0
        var total = 0
        var recordsSaved = true
        while (true) {
            if (recordsSaved) {
                recordsSaved = false
                var records: ConsumerRecords<String, String> = ConsumerRecords.empty()
                var records2: ConsumerRecords<String, String> = ConsumerRecords.empty()

                try {
                    records = consumer!!.poll(Duration.ofNanos(1))
                    records2 = consumer2!!.poll(Duration.ofNanos(1))

                } catch (e: KafkaException) {
                    e.printStackTrace()
                }
                for (record in records) {
                    val dataRecord: DataRecord = gson.fromJson(record.value(), DataRecord::class.java)
                    DataStore.recordsArray.add(dataRecord)
                    if (DataStore.recordsArray.size == 1)
                        println("\nOffset :: " + record.offset())
                    c++
                }

                for (record in records2) {
                    val dataRecord: DataRecord = gson.fromJson(record.value(), DataRecord::class.java)
                    DataStore.recordsArray.add(dataRecord)
                    if (DataStore.recordsArray.size == 1)
                        println("\nOffset :: " + record.offset())
                    j++
                }




                if (DataStore.recordsArray.size > 0) {
                    println("Got " + DataStore.recordsArray.size + " records")
                    total = total + c
                    var Heap: HashMap<String, DataRecord> = AggregatorService.aggregate(DataStore.recordsArray)
                    var t1 = Date().time
                    DatabaseService.save(Heap)
                    var t2 = Date().time
                    println("Time :: " + (t2 - t1))
                    consumer?.commitSync()
                    consumer2?.commitSync()

                }
            }
            if (DataStore.recordsArray.size == 0)
                recordsSaved = true
           // println("Saved " + total + " records so far\n")

        }
    }
}