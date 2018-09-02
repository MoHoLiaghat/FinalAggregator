package com.service

import com.dao.NormalizedUrlDao
import com.dao.OrginalUrlDao
import com.model.DataRecord
import com.model.DataStore
import java.sql.SQLException


object DatabaseService {



    /**
     * persisting a hashmap of dataRecords into database
     * @param  hashmap of dataRecords
     *@exception <SQLException>
     */


    fun save(heap: HashMap<String, DataRecord>) {
        var c = 0
        val con = DBConnection.getConnection()
        try {
            con?.autoCommit = false
            NormalizedUrlDao.setConnection(con)
            heap.forEach {
                NormalizedUrlDao.add(it.value)
            }
            OrginalUrlDao.setConnection(con)
            heap.forEach {
                OrginalUrlDao.Add(it.value)
            }
            OrginalUrlDao.flush()
            con?.commit()
        } catch (e: SQLException){
            con?.rollback()
            e.printStackTrace()
        } finally {
            con?.close()
        }
        DataStore.recordsArray.removeAll(DataStore.recordsArray)
    }

}