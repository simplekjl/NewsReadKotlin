package com.dev.newsread.articles

import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import rx.Observable
import java.io.Closeable

/**
 * Created by jlcs on 1/26/18.
 */

interface ArticlesUseCase : Closeable {
    fun hasArticles(): Boolean
    fun markArticlesRead(vararg url: String)
    fun markArticlesUnread(vararg url: String)
    fun getUnreadCount(categories: Collection<String>): Map<String, Long>

    fun getArticles(category: String?): Observable<out List<Article>>
    fun getSources(category: String?, selectedCategories: Collection<String>): Observable<out List<Source>>
    fun onSelectedCategoriesChanged(deletedCategories: Collection<String>): Observable<Unit>
    fun deleteOldArticles(daysToDelete: Int): Observable<Unit>
}