import ir.sls.aggregator.config.ReadConfig
import ir.sls.aggregator.model.DataRecord
import ir.sls.aggregator.service.ConsumerServiceImp

/**
 * KDocs Syntax by Aryan Gholamlou
 * Logging by Mohammad hossein Liaghat
 * local kafka server by Mohammad hossein Liaghat
 * Hocon by Mohammad hossein Liaghat
 */

fun main(args: Array<String>)
{
    ConsumerServiceImp().start()
}
