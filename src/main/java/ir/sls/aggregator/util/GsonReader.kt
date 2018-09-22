package ir.sls.aggregator.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun GsonReader() : Gson = Gson().newBuilder().disableHtmlEscaping().create()


