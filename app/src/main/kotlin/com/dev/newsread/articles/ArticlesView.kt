package com.dev.newsread.articles

import com.dev.newsread.BaseView
import com.dev.newsread.data.Article

/**
 * Created by jlcs on 1/26/18.
 */

interface ArticlesView : BaseView {
    fun onUnreadCountChanged(counts: Map<String, Long>)

    fun onNoArticlesAvailable()

    fun onArticlesDownloaded(count: Int)

    fun onCategoriesUpdated()

    fun onArticlesUpdated(articles: List<Article>)

    fun onSyncFailed(fail: Throwable)
}