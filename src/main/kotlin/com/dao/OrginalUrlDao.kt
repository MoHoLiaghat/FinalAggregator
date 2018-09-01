package com.dao

import com.model.DataRecord
import java.sql.Connection
import java.sql.SQLException


object OrginalUrlDao {
    var con: Connection? = null
    var addQuery = "INSERT INTO orginalUrl (url,normalizedUrl) VALUES "


    fun setConnection(conn: Connection?) {
        con = conn
    }

    fun Add(dataRecord: DataRecord) {
        dataRecord.originalUrls.forEach {
            addQuery = addQuery + "(\"" + it + "\"," + NormalizedUrlDao.getByUrl(dataRecord.normalizedUrl) + "),"
        }
    }

    fun flush() {
        if (addQuery.equals("INSERT INTO orginalUrl (url,normalizedUrl) VALUES "))
            return
        addQuery = addQuery.substring(0, addQuery.length - 1)
        addQuery = addQuery + ";"
        con?.prepareStatement(addQuery)!!.executeUpdate()
        addQuery = "INSERT INTO orginalUrl (url,normalizedUrl) VALUES "
    }
}