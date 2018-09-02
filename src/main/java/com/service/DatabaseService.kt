package com.service

import com.dao.NormalizedUrlDao
import com.dao.OrginalUrlDao
import com.model.DataRecord
import com.model.DataStore
import mu.KotlinLogging
import java.sql.SQLException


object DatabaseService {

private val logger = KotlinLogging.logger{}

    /**
     * persisting a hashmap of dataRecords into database
     * @param  hashmap of dataRecords
     *@exception <SQLException>.
     * @author Reza Varmazyari
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
            logger.error (e){ "DB Error" }
        } finally {
            con?.close()
        }
        DataStore.recordsArray.removeAll(DataStore.recordsArray)
    }

}