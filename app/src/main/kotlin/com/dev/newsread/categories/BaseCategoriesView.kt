package com.dev.newsread.categories

import com.dev.newsread.BaseView


interface BaseCategoriesView : BaseView {
	fun onCategorySelected(categorySet: Set<String>)
	fun showNoCategorySelected()
}