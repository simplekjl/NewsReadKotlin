package com.dev.newsread.sync

import com.dev.newsread.api.NewsApi
import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import com.dev.newsread.extensions.executeAsync
import com.dev.newsread.extensions.launchAsync
import com.dev.newsread.extensions.waitAllAsync
import com.dev.newsread.storage.Repository
import kotlinx.coroutines.experimental.async
import java.util.*
import javax.inject.Provider


/**
 * Created by jlcs on 1/25/18.
 */
class SyncUseCaseImpl(private val newsApi: NewsApi,
                      private val sourcesRepository: Provider<Repository<Source>>,
                      private val articlesRepository: Provider<Repository<Article>>) : SyncUseCase {
    override suspend fun downloadSourcesAsync(categories: Collection<String>) {
        sourcesRepository.get().use { repo ->
            val downloadJobs = categories.map { cat -> newsApi.getSources(cat).launchAsync() }
            val sources = downloadJobs.waitAllAsync().flatMap { job -> job.sources }
            repo.deleteAll()
            repo.addAll(sources)
        }
    }

    override suspend fun downloadArticlesAsync(source: Source): Int {
        val response = newsApi.getArticles(source.id).executeAsync()
        response.articles.forEach { it.category = source.category }
        var downloadCount = 0
        async {
            val repository = articlesRepository.get()
            repository.use { repo ->
                for (article in response.articles) {
                    if (repo.getById(article.url) == null) {
                        if (article.publishedAt == null || article.publishedAt!! == 0L) {
                            article.publishedAt = Date().time
                        }
                        repo.add(article)
                        downloadCount++
                    }
                }
            }
        }.await()
        return downloadCount
    }

    suspend override fun search(query: String): List<Article> {
        return newsApi.search(query)
                .executeAsync()
                .articles
                .distinctBy { article -> article.title }
    }
}