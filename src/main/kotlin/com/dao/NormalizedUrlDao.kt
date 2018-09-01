package com.dao

import com.model.DataRecord
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException


object NormalizedUrlDao {
    var con: Connection? = null
    var addQuery = "INSERT INTO normalizedUrl (url,count) VALUES "


    fun setConnection(conn: Connection?) {
        con = conn
    }

    fun add(dataRecord: DataRecord) {

        val checkQuery = "SELECT count FROM normalizedUrl where url=\"" + dataRecord.normalizedUrl + "\";"
        val res: ResultSet = con?.prepareStatement(checkQuery)!!.executeQuery()
        if (res.next()) {
            val updateQuery = "UPDATE normalizedUrl SET count=" + (res.getInt(1) + dataRecord.count) + " WHERE url=\"" + dataRecord.normalizedUrl + "\";"
            con?.prepareStatement(updateQuery)!!.executeUpdate()

        } else {
            addQuery = addQuery + "( \"" + dataRecord.normalizedUrl + "\" , " + dataRecord.count + "),"
        }
    }

    fun flush() {
        if (addQuery.equals("INSERT INTO normalizedUrl (url,count) VALUES "))
            return
        addQuery = addQuery.substring(0, addQuery.length - 1)
        addQuery = addQuery + ";"

        con?.prepareStatement(addQuery)!!.executeUpdate()

        addQuery = "INSERT INTO normalizedUrl (url,count) VALUES "
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