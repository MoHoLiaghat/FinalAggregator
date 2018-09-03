package com.service

import com.model.DataRecord
import java.util.ArrayList

object AggregatorService {


    /**
     * aggregating the arraylist of dataRecords
     *  @param  <ArrayList> of dataRecord objects
     * @return  Hashmap of aggregated records
     * @author Reza Varmazyari
     */

    fun aggregate(arrayOfDataRecords: ArrayList<DataRecord>): HashMap<String, DataRecord> {
        var heap = hashMapOf<String, DataRecord>()
        arrayOfDataRecords.forEach {
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