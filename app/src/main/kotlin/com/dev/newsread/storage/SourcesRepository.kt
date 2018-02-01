package com.dev.newsread.storage

import com.dev.newsread.data.Source

/**
 * Created by jlcs on 1/29/18.
 */

class SourcesRepository : RepositoryBase<Source>() {
    override val clazz: Class<Source>
        get() = Source::class.java

    override val primaryKey: String
        get() = "id"
}
