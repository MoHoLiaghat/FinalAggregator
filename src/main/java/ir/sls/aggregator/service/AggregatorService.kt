package ir.sls.aggregator.service

import ir.sls.aggregator.model.DataRecord
import java.util.ArrayList

/**
 * aggregating the arraylist of dataRecords
 *  @param  <ArrayList> of dataRecord objects
 * @return  Hashmap of aggregated records
 * @author Reza Varmazyari
 */
object AggregatorService {


    fun aggregate(dataRecords: ArrayList<DataRecord>): HashMap<String, DataRecord> {
        var heap = hashMapOf<String, DataRecord>()
        dataRecords.forEach {
            if (heap[it.normalizedUrl] == null)
                heap[it.normalizedUrl] = it
            else {
                var dataRecord: DataRecord = heap[it.normalizedUrl]!!
                dataRecord.count += it.count
                it.originalUrls.forEach {
                    dataRecord.originalUrls.add(it)
                }
                heap[it.normalizedUrl] = dataRecord
            }
        }
        return heap
    }
}