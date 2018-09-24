package ir.sls.aggregator.service


import mu.KLogger
import mu.KotlinLogging
import kotlin.collections.ArrayList

abstract class ProcessService<T>{

    val logger: KLogger = KotlinLogging.logger {}

    abstract  fun processData(recordsArray: ArrayList<T>) : Boolean


    fun sendToDao(){

    }

}