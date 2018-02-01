package com.dev.newsread.articles

import android.util.ArrayMap
import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import com.dev.newsread.storage.Repository
import io.realm.Sort
import java.util.*

/**
 * Class which implements the UseCase
 * Created by jlcs on 1/26/18.
 */


private const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000

class ArticlesUseCaseImpl(private val articlesRepository: Repository<Article>,
                          private val sourcesRepository: Repository<Source>): ArticlesUseCase {

    override fun hasArticles(): Boolean = articlesRepository.count() > 0L

    suspend override fun deleteOldArticlesAsync(daysToDelete: Int) {
        val deleteThreshold = Date().time - daysToDelete * MILLIS_IN_DAY
        articlesRepository.delete {
            lessThan("publishedAt", deleteThreshold)
        }
    }

    suspend override fun onCategoriesChangedAsync(deletedCategories: Collection<String>) {
        if (deletedCategories.isNotEmpty()) {
            articlesRepository.delete {
                `in`("category", deletedCategories.toTypedArray())
            }
        }
    }

    override suspend fun markArticleReadReadAsync(vararg url: String) {
        articlesRepository.updateAsync(*url) {
            isUnread = false
        }
    }

    suspend override fun markArticlesUnreadAsync(vararg url: String) {
        articlesRepository.updateAsync(*url) {
            isUnread = true
        }
    }

    suspend override fun getArticlesAsync(category: String?): List<Article> {
        return articlesRepository.query({
            if (category != null) {
                equalTo("category", category)
            } else {
                equalTo("isUnread", true)
            }
        }, arrayOf("isUnread", "publishedAt"), arrayOf(Sort.DESCENDING, Sort.DESCENDING))
    }

    suspend override fun getSourcesAsync(category: String?, selectedCategories: Collection<String>): List<Source> {
        return sourcesRepository.query({
            if (category != null) {
                equalTo("category", category)
            } else {
                `in`("category", selectedCategories.toTypedArray())
            }
        }, null, null)
    }

    override fun getUnreadCount(categories: Collection<String>): Map<String, Long> {
        val result = ArrayMap<String, Long>()
        categories.forEach { cat ->
            val count = articlesRepository.count {
                equalTo("category", cat)
                equalTo("isUnread", true)
            }
            result.put(cat, count)
        }
        return result
    }

    override fun close() {
        articlesRepository.close()
        sourcesRepository.close()
    }
}