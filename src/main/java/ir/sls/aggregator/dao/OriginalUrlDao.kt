package ir.sls.aggregator.dao

import ir.sls.aggregator.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException

/**
 * data access object of orginal urls.
 * @author Reza Varmazyari
 *
 */

object OriginalUrlDao {
    var con: Connection? = null
    private var preparedStatement:PreparedStatement? = null

    fun setConnection(conn: Connection?) {
        con = conn
        preparedStatement = con?.prepareStatement("INSERT INTO orginalUrl (url,hash,normalizedUrl) VALUES (? , ? , ?) on duplicate key update hash = hash;")
    }

    // Amin: add and flush can be merged.
    fun add(dataRecord: DataRecord) {
        dataRecord.originalUrls.forEach {
            preparedStatement?.setString(1,it)
            preparedStatement?.setString(2,DigestUtils.sha1Hex(it))
            preparedStatement?.setString(3,DigestUtils.sha1Hex(dataRecord.normalizedUrl))
            preparedStatement?.addBatch()
        }
    }

    fun flush() {
      preparedStatement?.executeBatch()
    }
}