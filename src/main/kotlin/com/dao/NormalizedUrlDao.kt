package com.dao

import com.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException


object NormalizedUrlDao {
    var con: Connection? = null
    val addQueryBuilder = StringBuilder()
    init {
        addQueryBuilder.append("INSERT INTO normalizedUrl (id,url,count) VALUES ")
    }

    fun setConnection(conn: Connection?) {
        con = conn
    }

    fun add(dataRecord: DataRecord) {

        val checkQuery = "SELECT count FROM normalizedUrl where url=\"${dataRecord.normalizedUrl}\";"
        val res: ResultSet = con?.prepareStatement(checkQuery)!!.executeQuery()
        if (res.next()) {
            val updateQuery = "UPDATE normalizedUrl SET count=${res.getInt(1) + dataRecord.count} WHERE url=\"${dataRecord.normalizedUrl}\";"
            con?.prepareStatement(updateQuery)!!.executeUpdate()

        } else {
            addQueryBuilder.append("(\"")
            addQueryBuilder.append(DigestUtils.sha256Hex(dataRecord.normalizedUrl))
            addQueryBuilder.append("\"")
            addQueryBuilder.append(" , \"")
            addQueryBuilder.append(dataRecord.normalizedUrl)
            addQueryBuilder.append("\" , ")
            addQueryBuilder.append(dataRecord.count)
            addQueryBuilder.append("),")
        }
    }

    fun flush() {
        if (addQueryBuilder.equals("INSERT INTO normalizedUrl (url,count) VALUES "))
            return
        addQueryBuilder.setLength(addQueryBuilder.length-1)
        addQueryBuilder.append(";")
        con?.prepareStatement(addQueryBuilder.toString())!!.executeUpdate()
        addQueryBuilder.setLength(0)
        addQueryBuilder.append("INSERT INTO normalizedUrl (id,url,count) VALUES ")
    }

    fun getByUrl(url: String): Int {
        val checkQuery = "SELECT id FROM normalizedUrl WHERE url=\"" + url + "\";"
        try {
            val res: ResultSet = con?.prepareStatement(checkQuery)!!.executeQuery()
            if (res.next())
                return res.getInt(1)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return 0
    }
}