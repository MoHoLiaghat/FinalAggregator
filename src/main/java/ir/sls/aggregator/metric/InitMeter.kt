package ir.sls.aggregator.metric

import com.codahale.metrics.MetricRegistry
import ir.sls.aggregator.config.ReadConfig

/**
 * @author Aryan Gholamlou
 */



object InitMeter {


    /**
     * initialize drop wizard metric
     */

    val metrics = MetricRegistry()
    var kafkaMeter = metrics.meter("kafka-meter")
    var databaseMeter = metrics.meter("database-meter")


    data class MeterClass(var KafkaMeter:Double, var DatabaseMeter:Double)

    /**
     * [getPerMinuteKafka] is a function that return measure of reading from kafka and writing into database per minute
     */
    fun getPerMinuteKafka(): MeterClass {
        val meterperminkafka = InitMeter.kafkaMeter.oneMinuteRate
        val meterpermindatabase = InitMeter.databaseMeter.oneMinuteRate
        return MeterClass(KafkaMeter = meterperminkafka, DatabaseMeter = meterpermindatabase)
    }

    /**
     * [markKafkaRead] is a function for marking data that read from kafka
     */
    fun markKafkaRead(l:Long){
        kafkaMeter.mark(l)

    }

    /**
     * [markDatabaseWrite] is a function for marking data that write to database
     */
    fun markDatabaseWrite(l:Long){
        databaseMeter.mark(l)

    }

}