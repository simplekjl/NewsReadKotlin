package com.dev.newsread.api

import com.dev.newsread.BuildConfig
import com.dev.newsread.data.ArticleResponse
import com.dev.newsread.data.SourceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Class which represents the calls to the service implemented with retrofit
 * Created by jlcs on 1/24/18.
 */

interface NewsApi {

    @GET("top-headlines")
    fun getArticles(@Query("sources") source: String,
                    @Query("apiKey") apiKey: String = BuildConfig.NEWS_READER_API_KEY,
                    @Query("language") lang: String = "en"): Observable<ArticleResponse>

    @GET("sources")
    fun getSources(@Query("category") category: String,
                   @Query("apiKey") apiKey: String = BuildConfig.NEWS_READER_API_KEY,
                   @Query("language") lang: String = "en"): Observable<SourceResponse>

    @GET("top-headlines")
    fun search(@Query("q") query: String,
               @Query("apiKey") apiKey: String = BuildConfig.NEWS_READER_API_KEY,
               @Query("language") lang: String = "en") : Observable<ArticleResponse>
}