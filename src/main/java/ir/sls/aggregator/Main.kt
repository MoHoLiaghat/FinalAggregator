import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import ir.sls.aggregator.config.Config
import ir.sls.aggregator.service.ConsumerService
import ir.sls.aggregator.service.metricService

/**
 *  Logging by Mohammad hossein Liaghat
 *  KDocs Syntax by Aryan Gholamlou
 *  local kafka server by Mohammad hossein Liaghat
 */
fun main(args: Array<String>)
{
    ConsumerService().start()
    metricService()
}

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
