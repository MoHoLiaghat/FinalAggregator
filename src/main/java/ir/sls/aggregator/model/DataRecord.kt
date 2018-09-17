package ir.sls.aggregator.model

/**
 * The class of our data
 * @author Aryan Gholamlou
 *
 */
// Amin: originalUrls should be Set
data class DataRecord(var normalizedUrl:String, var originalUrls:ArrayList<String>, var count:Int)
