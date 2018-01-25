package com.dev.newsread.data

import com.fasterxml.jackson.annotation.JsonIgnore
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by jlcs on 1/24/18.
 * open for inheritance
 */
open class Source : RealmObject() {
    @PrimaryKey
    lateinit var id:String
    lateinit var name: String
    lateinit var description:String
    lateinit var url: String
    lateinit var category : String
    lateinit var language : String
    @JsonIgnore
    var lastSyncDate = 0L

}