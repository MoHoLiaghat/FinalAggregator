package com.dao

import com.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * data access object of orginal urls.
 * @author Reza Varmazyari
 *
 */



object OrginalUrlDao {
    var con: Connection? = null
    var addQueryBuilder = StringBuilder()
    var preparedStatement:PreparedStatement? = null

    init {
        addQueryBuilder.append("INSERT INTO orginalUrl (url,normalizedUrl) VALUES ")
    }


    fun setConnection(conn: Connection?) {
        con = conn
        preparedStatement = con?.prepareStatement("INSERT INTO orginalUrl (url,normalizedUrl) VALUES (? , ?);")

    }

    fun add(dataRecord: DataRecord) {
        dataRecord.originalUrls.forEach {
            preparedStatement?.setString(1,it)
            preparedStatement?.setString(2,DigestUtils.sha1Hex(dataRecord.normalizedUrl))
            preparedStatement?.addBatch()
        }
    }

    fun flush() {
      preparedStatement?.executeBatch()
    }
}