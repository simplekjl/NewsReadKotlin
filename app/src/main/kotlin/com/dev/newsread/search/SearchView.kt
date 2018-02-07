package com.dev.newsread.search

import com.dev.newsread.BaseView
import com.dev.newsread.data.Article

interface SearchView : BaseView {
	fun onSearchFailed(throwable: Throwable)
	fun onSearchResults(articles: List<Article>)
	fun onNoSearchResults()
}