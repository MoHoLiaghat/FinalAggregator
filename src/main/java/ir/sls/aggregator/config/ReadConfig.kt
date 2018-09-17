package ir.sls.aggregator.config

import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions

object ReadConfig
{
    //read config file
    var hoconConfig = ConfigFactory.defaultApplication()

    //parse to gson
    var gson = Gson().newBuilder().create()
    val tmp: String = hoconConfig.root().render(ConfigRenderOptions.concise())

    //create Config object
    val config = gson.fromJson(tmp, Config::class.java)
}