package com.dev.newsread.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jlcs on 1/24/18.
 */
class DateToLongDeserializer : JsonDeserializer<Long>(){
    override fun deserialize(jsonParser: JsonParser?, ctxt: DeserializationContext?): Long {
        val format = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss")
        val date = jsonParser?.text ?:  return Date().time
        return format.parse(date).time
    }

}