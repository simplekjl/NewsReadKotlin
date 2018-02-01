package com.dev.newsread.articles

import com.dev.newsread.BaseView

/**
 * Created by jlcs on 1/26/18.
 */

interface ArticlesView : BaseView {
    fun onUnreadCountChanged(counts: Map<String, Long>)

    fun onNoArticlesAvailable()

    fun onArticlesDownloaded(count: Int)
}