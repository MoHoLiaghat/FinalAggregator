package ir.sls.aggregator.dao

import ir.sls.aggregator.model.DataRecord
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * data access object of original urls. creates a batch of originalUrls and
 * then when the batch reaches to a specified value , persists the batch to database
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

    fun persist(heap:HashMap<String,DataRecord>){
        heap.forEach{
            it.value.originalUrls.forEach { itt:String ->
                preparedStatement?.setString(1,itt)
                preparedStatement?.setString(2,DigestUtils.sha1Hex(itt))
                preparedStatement?.setString(3,DigestUtils.sha1Hex(it.value.normalizedUrl))
                preparedStatement?.addBatch()
            }
        }
        preparedStatement?.executeBatch()
    }
}