package ir.sls.aggregator.service

import ir.sls.aggregator.model.DataRecord
import java.util.*

class UrlProcessService : ProcessService<DataRecord>(){


    override fun processData(recordsArray: ArrayList<DataRecord>): Boolean {

        logger.info("Got ${recordsArray.size} records")
        val heap: HashMap<String, DataRecord> = AggregatorService.aggregate(recordsArray)
        val t1 = Date().time
        val saveSuccess = DatabaseService.save(heap)
        logger.info("Saved :: $saveSuccess")
        val t2 = Date().time
        logger.info("Time :: " + (t2 - t1))
        return saveSuccess
    }

}
