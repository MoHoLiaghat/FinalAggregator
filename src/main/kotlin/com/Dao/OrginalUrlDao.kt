package com.Dao

import com.Model.Row
import java.sql.Connection


object OrginalUrlDao {
    var con: Connection
    var addQuery = "INSERT INTO orginalUrl (url,normalizedUrl) VALUES "
    init {
        con = DBConnectio.getConnection()
    }

    fun Add(row: Row) {
        //var t1 = Date().time
        row.originalUrls.forEach {
            addQuery = addQuery +  "(\"" + it + "\"," + NormalizedUrlDao.GetByUrl(row.normalizedUrl) + "),"
        }


    }

    fun flush(){
        addQuery = addQuery.substring(0, addQuery.length-1)
        addQuery = addQuery + ";"
        con.prepareStatement(addQuery).executeUpdate()
        addQuery = "INSERT INTO orginalUrl (url,normalizedUrl) VALUES "
    }
}