package ir.sls.aggregator.config

import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import ir.sls.aggregator.util.gson

object ReadConfig
{
    //read config file
    var hoconConfig = ConfigFactory.defaultApplication()

    //parse to gson
    val tmp: String = hoconConfig.root().render(ConfigRenderOptions.concise())
    //create Config object
    val config = gson.fromJson(tmp, Config::class.java)

}