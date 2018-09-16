package ir.sls.aggregator.service

import com.google.gson.Gson
import ir.sls.aggregator.metric.InitMeter.getPerMinuteKafka
import spark.Spark.port
import spark.kotlin.get

fun metricService()
{
    port(4641)

    var gson = Gson().newBuilder().create()

    get("/number") {

        val metricConfig: String = gson.toJson(getPerMinuteKafka())

        metricConfig
    }
}