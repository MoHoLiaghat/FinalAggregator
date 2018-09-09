package ir.sls.aggregator.model

/**
 * a class of our data
 */
// Amin: originalUrls should be Set
data class DataRecord(var normalizedUrl:String, var originalUrls:ArrayList<String>, var count:Int)
