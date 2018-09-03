package com.service

import com.dao.NormalizedUrlDao
import com.dao.OrginalUrlDao
import com.model.DataRecord
import com.model.DataStore
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException
import mu.KotlinLogging
import java.io.IOException
import java.sql.SQLException


object DatabaseService {

private val logger = KotlinLogging.logger{}

    /**
     * persisting a hashmap of dataRecords into database
     * @param  hashmap of dataRecords
     *@exception <SQLException>.
     * @author Reza Varmazyari
     */


    fun save(heap: HashMap<String, DataRecord>):Boolean {


        var allSaveSuccess = false
        val con = DBConnection.getConnection()
        try {
            con?.autoCommit = false
            NormalizedUrlDao.setConnection(con)
            heap.forEach {
                NormalizedUrlDao.add(it.value)
            }
            NormalizedUrlDao.flush()
            OrginalUrlDao.setConnection(con)
            heap.forEach {
                OrginalUrlDao.add(it.value)
            }
            OrginalUrlDao.flush()
            con?.commit()
            allSaveSuccess = true
        } catch (e: KotlinNullPointerException){
            logger.error (e){ "DB Connection Error" }
            allSaveSuccess = false
        } catch (e: SQLException){
            con?.rollback()
            logger.error (e){ "DB Write Error" }
            allSaveSuccess = false
        } finally {
            con?.close()
        }
        return if(allSaveSuccess){
            DataStore.recordsArray.removeAll(DataStore.recordsArray)
            true
        }
        else
            false
    }

}