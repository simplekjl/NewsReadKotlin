package com.dev.newsread.injection

import com.dev.newsread.articles.ArticlesActivity
import com.dev.newsread.categories.SelectCategoriesActivity
import com.dev.newsread.search.SearchActivity
import com.dev.newsread.settings.SettingsActivity
import com.dev.newsread.startup.StartupActivity
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

	fun inject(startupActivity: StartupActivity)
	fun inject(articlesActivity: ArticlesActivity)
	fun inject(selectCategoriesActivity: SelectCategoriesActivity)
	fun inject(settingsActivity: SettingsActivity)
	fun inject(searchActivity: SearchActivity)
}