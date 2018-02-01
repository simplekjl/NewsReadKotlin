package com.dev.newsread.storage

import com.dev.newsread.data.Article

/**
 * Created by jlcs on 1/29/18.
 */
class ArticlesRepository : RepositoryBase<Article>() {

    override val primaryKey: String
        get() = "url"

    override val clazz: Class<Article>
        get() = Article::class.java
}