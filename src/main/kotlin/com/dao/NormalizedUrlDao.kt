package com.dao

import com.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException


/**
 * data access object of normalized urls.
 */


object NormalizedUrlDao {
    var con: Connection? = null
    val addQueryBuilder = StringBuilder()
    init {
        addQueryBuilder.append("INSERT INTO normalizedUrl (hash,url,count) VALUES ")
    }

    fun setConnection(conn: Connection?) {
        con = conn
    }

    fun add(dataRecord: DataRecord) {
        val hash = DigestUtils.sha1Hex(dataRecord.normalizedUrl)
        val addQueryBuilder = "INSERT INTO normalizedUrl (hash,url,count) VALUES (\"${hash}\" , \"${dataRecord.normalizedUrl}\" , ${dataRecord.count}) on duplicate key update count = values(count) + 1 ;"
        con?.prepareStatement(addQueryBuilder)!!.executeUpdate()
    }

}