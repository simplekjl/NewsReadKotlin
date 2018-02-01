package com.dev.newsread.articles

import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import java.io.Closeable

/**
 * Created by jlcs on 1/26/18.
 */

interface ArticlesUseCase : Closeable {
    fun hasArticles(): Boolean
    fun getUnreadCount(categories: Collection<String>): Map<String, Long>

    suspend fun getArticlesAsync(category: String?): List<Article>
    suspend fun getSourcesAsync(category: String?, selectedCategories: Collection<String>): List<Source>
    suspend fun onCategoriesChangedAsync(deletedCategories: Collection<String>)
    suspend fun deleteOldArticlesAsync(daysToDelete: Int)
    suspend fun markArticleReadReadAsync(vararg url: String)
    suspend fun markArticlesUnreadAsync(vararg url: String)
}