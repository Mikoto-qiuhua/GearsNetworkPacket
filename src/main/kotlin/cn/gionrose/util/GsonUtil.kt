package cn.gionrose.util

import com.google.gson.Gson

val gson by lazy { Gson() }

fun Map<String, Any?>.toJson (): String
{
    return gson.toJson(this)
}

fun  String.parseMapFromJson  (): Map<String, Any?> {
    return gson.fromJson(this, Map::class.java) as Map<String, Any?>
}

fun String.parseListFromJson (): List<String>
{
    return gson.fromJson(this, List::class.java) as List<String>
}