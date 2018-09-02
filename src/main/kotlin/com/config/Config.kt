package com.config

object Config {
    const val JdbcUrl = "jdbc:mysql://localhost:3306/aggregator"
    const val Username = "root"
    const val Password = "123"
    const val Driver = "com.mysql.jdbc.Driver"
    const val Bootstrap_servers = "192.168.1.51:9092"
    var Group_id = "fghsdfdsasasddsadasasdsadfsdgh"
    const val Enable_auto_commit = false
    const val Auto_commit_interval_ms = "1000"
    const val Key_deserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val Value_deserializer = "org.apache.kafka.common.serialization.StringDeserializer"
    const val Auto_offset_reset = "earliest"
    const val Max_poll_records = "50000"
    const val Subscribtion =  "rahkar"
    const val Max_poll_interval_ms = Integer.MAX_VALUE
    const val Read_from_beginning = false;

}