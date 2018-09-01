/*
   Subject:
   Auther:
   Comemct:


 */
import com.Config.Config
import com.Dao.NormalizedUrlDao
import com.Dao.OrginalUrlDao
import com.Model.Row
import com.google.gson.Gson
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.thread

/*

  Gathering date and aggregate and persist
    Input: null
    Action:
    Output: persist in mysql


 */
fun Consume() {

    //Creating Json Parser Using Gson Library
    val gson = Gson()
    //
    val props = Properties()
    props["bootstrap.servers"] = Config.Bootstrap_servers
    props["group.id"] = Config.Group_id
    props["enable.auto.commit"] = Config.Enable_auto_commit
    props["auto.commit.interval.ms"] = Config.Auto_commit_interval_ms
    props["key.deserializer"] = Config.Key_deserializer
    props["value.deserializer"] = Config.Value_deserializer
    props["auto.offset.reset"] = Config.Auto_offset_reset
    props["max.poll.records"] = Config.Max_poll_records
    val consumer = KafkaConsumer<String, String>(props)
    consumer.subscribe(Arrays.asList(Config.Subscribtion))
    var c = 0
    var total = 0
    var flag = true
    while (true) {
        if (flag) {
            flag = false
            val records = consumer.poll(Duration.ofNanos(1))
            for (record in records) {
                val row: Row = gson.fromJson(record.value(), Row::class.java)
                DataStore.a.add(row)
                if(DataStore.a.size == 1)
                    println("\nOffset :: " + record.offset())
                c++
            }
            if (DataStore.a.size > 0) {
                println("Got " + DataStore.a.size + " records")
                total = total + c
                var Heap: HashMap<String, Row> = Aggregate(DataStore.a)
                var t1 = Date().time
                Save(Heap)
                var t2 = Date().time
                println("Time :: " + (t2 - t1))
                consumer.commitSync()
            }
        }
        if (DataStore.a.size == 0)
            flag = true
            println("Saved " + total + " records so far\n")

    }
}

fun Save(Heap: HashMap<String, Row>) {
    var c = 0
    Heap.forEach {
        NormalizedUrlDao.Add(it.value)
    }
    NormalizedUrlDao.flush()
    Heap.forEach {
        OrginalUrlDao.Add(it.value)
    }
    OrginalUrlDao.flush()
    DataStore.a.removeAll(DataStore.a)
}


fun Aggregate(a: ArrayList<Row>): HashMap<String, Row> {
    var Heap = hashMapOf<String, Row>()
    a.forEach {
        if (Heap.get(it.normalizedUrl) == null)
            Heap.put(it.normalizedUrl, it)
        else {
            var row: Row = Heap.get(it.normalizedUrl) as Row
            row.count++
            it.originalUrls.forEach {
                row.originalUrls.add(it)
            }
            Heap.put(it.normalizedUrl, row)
        }
    }
    return Heap
}


fun main(args: Array<String>) {
    var t1: Long = Date().time
    Consume()
    var t2: Long = Date().time
    println((t2 - t1))

}

object DataStore {
    var a = arrayListOf<Row>()
}