package com.dev.newsread.api

import com.dev.newsread.BuildConfig
import com.dev.newsread.data.ArticleResponse
import com.dev.newsread.data.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Class which represents the calls to the service implemented with retrofit
 * Created by jlcs on 1/24/18.
 */

interface NewsApi{

    @GET("articles")
    fun getArticles(@Query("source")source : String,
                    @Query("apiKey")apiKey : String = BuildConfig.NEWS_READER_API_KEY): Call<ArticleResponse>
    @GET("sources")
    fun getSources(@Query("category") category: String,
                   @Query("apiKey") apiKey: String = BuildConfig.NEWS_READER_API_KEY,
                   @Query("language") lang : String = "en") : Call<SourceResponse>

}