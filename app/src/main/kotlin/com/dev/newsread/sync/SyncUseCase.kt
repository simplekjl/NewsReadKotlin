package com.dev.newsread.sync

import com.dev.newsread.data.Article
import com.dev.newsread.data.Source

/**
 * Created by jlcs on 1/25/18.
 */
interface SyncUseCase {
    suspend fun downloadSourcesAsync(categories: Collection<String>)
    suspend fun downloadArticlesAsync(source: Source): Int
    suspend fun search(query: String): List<Article>
}
