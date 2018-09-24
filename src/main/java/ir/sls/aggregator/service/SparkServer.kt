package ir.sls.aggregator.service

import ir.sls.aggregator.config.ReadConfig
import ir.sls.aggregator.metric.InitMeter.getPerMinuteKafka
import ir.sls.aggregator.util.gson
import spark.Spark.port
import spark.kotlin.get

/**
 * fun [metricService] to register Spark server
 * @author Mohammad Hossein Liaghat E-mail : mohamadliaghat@gmail.com
 * @return json of metrics
 */

fun metricService()
{
    port(ReadConfig.config.spark.port)

    get("/metric") {

        val metricConfig: String = gson.toJson(getPerMinuteKafka())

        metricConfig
    }
}