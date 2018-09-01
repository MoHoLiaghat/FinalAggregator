package com.service

import com.model.DataRecord
import java.util.ArrayList

object AggregatorService {

    /*
        Aggregating the arraylist of dataRecords
            Input:
                Arraylist of dataRecord objects

            Output:
                Hashmap of aggregated records
     */
    fun aggregate(a: ArrayList<DataRecord>): HashMap<String, DataRecord> {
        var heap = hashMapOf<String, DataRecord>()
        a.forEach {
            if (heap.get(it.normalizedUrl) == null)
                heap.put(it.normalizedUrl, it)
            else {
                var dataRecord: DataRecord = heap.get(it.normalizedUrl) as DataRecord
                dataRecord.count++
                it.originalUrls.forEach {
                    dataRecord.originalUrls.add(it)
                }
                heap.put(it.normalizedUrl, dataRecord)
            }
        }
        return heap
    }
}