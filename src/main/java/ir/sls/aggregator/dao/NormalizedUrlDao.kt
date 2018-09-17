package ir.sls.aggregator.dao

import ir.sls.aggregator.model.DataRecord
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException
import mu.KotlinLogging
import org.apache.commons.codec.digest.DigestUtils
import java.sql.Connection
import java.sql.PreparedStatement

/**
 * data access object of normalized urls. creates a batch of normalizedUrls
 * and then when the batch reaches to a specified value , persists the batch to database
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

    fun persist(heap:HashMap<String,DataRecord>){
        heap.forEach{
            val hash = DigestUtils.sha1Hex(it.value.normalizedUrl)
            var normalizedUrl = it.value.normalizedUrl.replace("\"","")
            try{
                preparedStatement?.setString(1,hash)
                preparedStatement?.setString(2,normalizedUrl)
                preparedStatement?.setInt(3,it.value.count)
                preparedStatement?.addBatch()
            } catch (e: MySQLSyntaxErrorException){
                logger.error (e){ "Failed to write in database" }
            }
        }
        preparedStatement?.executeBatch()
    }

}