package com.dev.newsread.data

import com.dev.newsread.util.DateToLongDeserializer
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by jlcs on 1/24/18.
 */
open class Article: RealmObject(){
    @PrimaryKey
    lateinit var url : String
    lateinit var title : String
    var author : String? = null
    var description : String? = null
    var urlToImage : String? = null
    lateinit var category : String
    @JsonDeserialize(using = DateToLongDeserializer::class)
    var publishedAt : Long? = null
    @JsonIgnore
    var isUnread = true

}