package com.dev.newsread.storage

import com.dev.newsread.data.Article
import io.realm.Sort
import rx.Observable

/**
 * Created by jlcs on 1/29/18.
 */
class ArticlesRepository : RepositoryBase<Article>() {

    override val primaryKey: String
        get() = "url"

    override val clazz: Class<Article>
        get() = Article::class.java

    override fun getAll(): Observable<out List<Article>> {
        return realm.where(clazz)
                .findAllSortedAsync("isUnread", Sort.DESCENDING)
                .asObservable()
                .filter { r -> r.isLoaded }
                .first()
    }
}