package com.Dao

import com.Model.Row
import java.sql.Connection
import java.sql.ResultSet


object NormalizedUrlDao {
    var con: Connection
    var addQuery = "INSERT INTO normalizedUrl (url,count) VALUES "

    init {
        con = DBConnectio.getConnection()
        con.autoCommit = false
    }

    fun Add(row: Row) {
        val checkQuery = "SELECT count FROM normalizedUrl where url=\"" + row.normalizedUrl + "\";"
        //
        val res: ResultSet = con.prepareStatement(checkQuery).executeQuery()
        if (res.next()) {
            val updateQuery = "UPDATE normalizedUrl SET count=" + (res.getInt(1) + row.count) + " WHERE url=\"" + row.normalizedUrl + "\";"
            con.prepareStatement(updateQuery).executeUpdate()
        } else {
            addQuery = addQuery + "( \"" + row.normalizedUrl + "\" , " + row.count + "),"
        }

    }

    fun flush() {
        addQuery = addQuery.substring(0, addQuery.length - 1)
        addQuery = addQuery + ";"
        con.prepareStatement(addQuery).executeUpdate()
        con.commit()
        addQuery = "INSERT INTO normalizedUrl (url,count) VALUES "
    }

    fun GetByUrl(url: String): Int {
        val checkQuery = "SELECT id FROM normalizedUrl WHERE url=\"" + url + "\";"
        val res: ResultSet = con.prepareStatement(checkQuery).executeQuery()
        if (res.next())
            return res.getInt(1)
        return 0
    }
}