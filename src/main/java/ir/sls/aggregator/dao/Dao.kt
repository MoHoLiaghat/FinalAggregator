package ir.sls.aggregator.dao

import mu.KotlinLogging

abstract class Dao<T, U>
{
    private val logger = KotlinLogging.logger {}

    abstract fun processData(heap: HashMap<T, U>): Boolean
}