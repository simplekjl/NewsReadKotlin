package com.dev.newsread.sync

import com.dev.newsread.BuildConfig
import com.dev.newsread.api.NewsApi
import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import com.dev.newsread.storage.Repository
import com.dev.newsread.util.SchedulerProvider
import rx.Observable
import java.util.*


/**
 * Created by jlcs on 1/25/18.
 */
class SyncUseCaseImpl(private val newsApi: NewsApi,
                      private val sourcesRepository: Repository<Source>,
                      private val articlesRepository: Repository<Article>,
                      private val schedulerProvider : SchedulerProvider) : SyncUseCase {
    override fun close() {
        sourcesRepository.close()
        articlesRepository.close()
    }


    override fun downloadSources(categories: Collection<String>) : Observable<Unit> {
       return sourcesRepository.deleteAll()
               .flatMap { Observable.from(categories) }
               .observeOn(schedulerProvider.io)
               .flatMap {  c -> newsApi.getSources(c) }
               .observeOn(schedulerProvider.ui)
               .flatMap { s -> sourcesRepository.addAll(s.sources) }

    }

    override  fun downloadArticles(source: Source):Observable<Int> {
      return Observable.just(source.id)
              .observeOn(schedulerProvider.io)
              .flatMap { id -> newsApi.getArticles(id) }
              .observeOn(schedulerProvider.ui)
              .doOnNext{r -> r.articles.forEach{ article -> article.category = source.category}}
              .flatMap { r -> Observable.from(r.articles) }
              .filter{ article -> articlesRepository.getById(article.url) == null}
              .collect({ArrayList<Article>()},{r,a -> r.add(a)})
              .flatMap { Observable.zip(Observable.just(it), articlesRepository.addAll(it), {p1,p2 ->Pair(p1,p2)}) }
              .map { (first) -> first.count() }
    }

    override fun search(query: String): Observable<List<Article>> {

        return newsApi.search(query,BuildConfig.NEWS_READER_API_KEY,"en")
                .map { l -> l.articles  }
//        return Observable.just(newsApi.search(query,BuildConfig.NEWS_READER_API_KEY,"en"))
//                .observeOn(schedulerProvider.io)
//                .flatMap { result -> newsApi.search(query,BuildConfig.NEWS_READER_API_KEY,"en") }
//                .subscribeOn(schedulerProvider.ui)
//                .flatMap { s -> articlesRepository.addAll(s.articles) }
    }
}