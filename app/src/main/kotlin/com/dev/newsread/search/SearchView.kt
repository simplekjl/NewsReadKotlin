package com.dev.newsread.search

import com.dev.newsread.BaseView
import com.dev.newsread.data.Article

interface SearchView : BaseView {
	fun onSearchFailed()
	fun onSearchResults(articles: List<Article>)
	fun onNoSearchResults()
}