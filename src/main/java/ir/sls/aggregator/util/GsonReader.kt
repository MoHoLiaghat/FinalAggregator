package ir.sls.aggregator.util

import com.google.gson.Gson

fun GsonReader() : Gson = Gson().newBuilder().disableHtmlEscaping().create()
