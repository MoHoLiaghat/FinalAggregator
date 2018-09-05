package ir.sls.aggregator.service

import ir.sls.aggregator.model.DataRecord
import java.util.ArrayList
// Amin: out docs before object
object AggregatorService {


    /**
     * aggregating the arraylist of dataRecords
     *  @param  <ArrayList> of dataRecord objects
     * @return  Hashmap of aggregated records
     * @author Reza Varmazyari
     */
    // Amin: input name is plural and does not need arrayOf prefix
    fun aggregate(arrayOfDataRecords: ArrayList<DataRecord>): HashMap<String, DataRecord> {
        var heap = hashMapOf<String, DataRecord>()
        arrayOfDataRecords.forEach {
            // Amin: use heap.computeIfAbsent()
            if (heap[it.normalizedUrl] == null)
                heap[it.normalizedUrl] = it
            else {
                // Amin: casting is not required.
                var dataRecord: DataRecord = heap[it.normalizedUrl] as DataRecord
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