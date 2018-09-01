package com.dao

import com.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.SQLException


object OrginalUrlDao {
    var con: Connection? = null
    var addQueryBuilder = StringBuilder()
    init {
        addQueryBuilder.append("INSERT INTO orginalUrl (url,normalizedUrl) VALUES ")
    }


    fun setConnection(conn: Connection?) {
        con = conn
    }

    fun Add(dataRecord: DataRecord) {
        dataRecord.originalUrls.forEach {
            addQueryBuilder.append("(\"")
            addQueryBuilder.append(it)
            addQueryBuilder.append("\",")
            addQueryBuilder.append(DigestUtils.sha256Hex(dataRecord.normalizedUrl))
            addQueryBuilder.append("),")
        }
    }

    fun flush() {
        if (addQueryBuilder.equals("INSERT INTO orginalUrl (url,normalizedUrl) VALUES "))
            return
        addQueryBuilder.setLength(addQueryBuilder.length-1)
        addQueryBuilder.append(";")
        con?.prepareStatement(addQueryBuilder.toString())!!.executeUpdate()
        addQueryBuilder.setLength(0)
        addQueryBuilder.append("INSERT INTO orginalUrl (url,normalizedUrl) VALUES ")
    }
}