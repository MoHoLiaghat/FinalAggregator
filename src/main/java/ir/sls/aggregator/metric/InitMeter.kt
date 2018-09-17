package ir.sls.aggregator.metric

import com.codahale.metrics.MetricRegistry

/**
 * @author Aryan Gholamlou
 */
object InitMeter {
    val metrics = MetricRegistry()
    var kafkaMeter = metrics.meter("kafka-meter")
    var databaseMeter = metrics.meter("database-meter")


    data class MeterClass(var KafkaMeter:Double, var DatabaseMeter:Double)


    fun getPerMinuteKafka(): MeterClass {
        val meterperminkafka = InitMeter.kafkaMeter.oneMinuteRate
        val meterpermindatabase = InitMeter.databaseMeter.oneMinuteRate
        return MeterClass(KafkaMeter = meterperminkafka, DatabaseMeter = meterpermindatabase)
    }

    fun markKafkaRead(l:Long){
        kafkaMeter.mark(l)

    }
    fun markDatabaseWrite(l:Long){
        databaseMeter.mark(l)

    }

}