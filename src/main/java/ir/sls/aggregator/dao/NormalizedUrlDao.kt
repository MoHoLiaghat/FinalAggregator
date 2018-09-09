package ir.sls.aggregator.dao

import ir.sls.aggregator.model.DataRecord
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import mu.KotlinLogging
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.PreparedStatement

// Amin: Better documentation
/**
 * data access object of normalized urls.
 * @author Reza Varmazyari
 */
object NormalizedUrlDao {
    private val logger = KotlinLogging.logger{}

    var con: Connection? = null
    private var preparedStatement:PreparedStatement? = null

    fun setConnection(conn: Connection?) {
        con = conn
        preparedStatement = con?.prepareStatement("INSERT INTO normalizedUrl (hash,url,count) VALUES (? , ? , ?) on duplicate key update count = count + 1 ;")
    }

    // Amin: add and flush can be merged
    fun add(dataRecord: DataRecord) {
        // Amin: Don't use DigestUtils which is a third-party library where java has its own
        val hash = DigestUtils.sha1Hex(dataRecord.normalizedUrl)
        // Amin: why replace?
        var normalizedUrl = dataRecord.normalizedUrl.replace("\"","")
        try{
            preparedStatement?.setString(1,hash)
            preparedStatement?.setString(2,normalizedUrl)
            preparedStatement?.setInt(3,dataRecord.count)
            preparedStatement?.addBatch()
        } catch (e: MySQLSyntaxErrorException){
            logger.error (e){ "DB Error" }
        }
    }

    fun flush(){
        preparedStatement?.executeBatch()
    }

}