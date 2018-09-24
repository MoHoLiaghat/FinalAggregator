package ir.sls.aggregator.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson().newBuilder().disableHtmlEscaping().create()

inline fun <reified T> Gson.customFromJson(json:String) = this.fromJson<T>(json,object: TypeToken<T>(){}.type)

