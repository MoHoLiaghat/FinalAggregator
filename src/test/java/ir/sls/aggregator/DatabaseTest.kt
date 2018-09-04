package ir.sls.aggregator

import org.h2.tools.RunScript
import org.junit.After
import org.junit.Before
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager

open class DatabaseTest(val initialScriptPath: String) : AppTest() {
    protected lateinit var connection: Connection
    protected val jdbcUrl = "jdbc:h2:mem:test;mode=mysql"
    protected val username = "test"
    protected val password = "test"
    protected  val driver = "org.h2.Driver"

    @Before
    open fun setUp() {
        connection = DriverManager.getConnection(jdbcUrl, username, password)

        RunScript.execute(connection,
                          InputStreamReader(Int::class.java.getResourceAsStream(initialScriptPath)))
    }

    @After
    open fun tearDown() {
        connection.close()
    }
}

