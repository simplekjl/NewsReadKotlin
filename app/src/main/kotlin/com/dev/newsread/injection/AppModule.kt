package com.dev.newsread.injection

import android.content.Context
import com.dev.newsread.api.ApiFactory
import com.dev.newsread.api.NewsApi
import com.dev.newsread.articles.ArticlesUseCase
import com.dev.newsread.articles.ArticlesUseCaseImpl
import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import com.dev.newsread.storage.ArticlesRepository
import com.dev.newsread.storage.Repository
import com.dev.newsread.storage.SourcesRepository
import com.dev.newsread.sync.SyncUseCase
import com.dev.newsread.sync.SyncUseCaseImpl
import com.dev.newsread.util.SHARED_PREFS
import com.dev.newsread.util.SchedulerProvider
import com.dev.newsread.util.SchedulerProviderImpl
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val context: Context) {

	@Provides
	fun providesSharedPrefs() = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

	@Provides
	fun providesNewsApi() = ApiFactory.create<NewsApi>()

	@Provides
	fun providesSourcesRepo(): Repository<Source> = SourcesRepository()

	@Provides
	fun providesArticlesRepo(): Repository<Article> = ArticlesRepository()

	@Provides
	fun providesSyncUseCase(newsApi: NewsApi,
							sourcesRepository: Repository<Source>,
							articlesRepository: Repository<Article>,
							schedulerProvider: SchedulerProvider): SyncUseCase = SyncUseCaseImpl(newsApi,
			sourcesRepository,
			articlesRepository,
			schedulerProvider)

	@Provides
	fun providesArticlesUseCase(articlesRepository: Repository<Article>,
								sourcesRepository: Repository<Source>) : ArticlesUseCase =
			ArticlesUseCaseImpl(articlesRepository, sourcesRepository)

    @Provides
    fun providesSchedulerProvider(): SchedulerProvider{
        return SchedulerProviderImpl()
    }
}