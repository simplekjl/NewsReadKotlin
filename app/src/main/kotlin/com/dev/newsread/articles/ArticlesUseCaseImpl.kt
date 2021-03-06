package com.dev.newsread.articles

import android.util.ArrayMap
import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import com.dev.newsread.storage.Repository
import io.realm.Sort
import rx.Observable
import java.util.*

/**
 * Class which implements the UseCase
 * Created by jlcs on 1/26/18.
 */


private const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000

class ArticlesUseCaseImpl(private val articlesRepository: Repository<Article>,
                          private val sourcesRepository: Repository<Source>): ArticlesUseCase {

    override fun hasArticles(): Boolean = articlesRepository.count() > 0L

    override fun deleteOldArticles(daysToDelete: Int): Observable<Unit> {
        val deleteThreshold = Date().time - daysToDelete * MILLIS_IN_DAY
        return articlesRepository.delete {
            lessThan("publishedAt", deleteThreshold)
        }
    }

    override fun onSelectedCategoriesChanged(deletedCategories: Collection<String>): Observable<Unit> {
        return articlesRepository.delete {
            `in`("category", deletedCategories.toTypedArray())
        }
    }

    override fun markArticlesRead(vararg url: String) {
        url.forEach {
            articlesRepository.update(it) {
                isUnread = false
            }
        }
    }

    override fun markArticlesUnread(vararg url: String) {
        url.forEach { u ->
            articlesRepository.update(u) {
                isUnread = true
            }
        }
    }

    override fun getArticles(category: String?): Observable<out List<Article>> {
        return articlesRepository.query({
            if (category != null) {
                equalTo("category", category)
            } else {
                equalTo("isUnread", true)
            }
        }, arrayOf("isUnread", "publishedAt"), arrayOf(Sort.DESCENDING, Sort.DESCENDING))
    }

    override fun getSources(category: String?, selectedCategories: Collection<String>): Observable<out List<Source>> {
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