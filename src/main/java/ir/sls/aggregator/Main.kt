import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import ir.sls.aggregator.config.Config
import ir.sls.aggregator.service.ConsumerService

/**
 * KDocs Syntax by Aryan Gholamlou
 * Logging by Mohammad hossein Liaghat
 * local kafka server by Mohammad hossein Liaghat
 * Hocon by Mohammad hossein Liaghat
 */

fun main(args: Array<String>)
{
    ConsumerService().start()
}
