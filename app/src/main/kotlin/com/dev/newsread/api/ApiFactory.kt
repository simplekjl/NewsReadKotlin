package com.dev.newsread.api

import android.util.Log
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Singleton used for Retrofit to make service calls
 * Created by jlcs on 1/24/18.
 */

object ApiFactory{

    inline fun<reified T> create():T{
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(60,TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
        val logginInterceptor = HttpLoggingInterceptor(
                {l-> Log.d("HTTP",l)})
        logginInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        httpClientBuilder.addInterceptor(logginInterceptor)
        val httpClient = httpClientBuilder.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(("https://newsapi.org/v1/"))
                .addConverterFactory(JacksonConverterFactory.create(createObjectMapper()))
                .client(httpClient)
                .build()
        return retrofit.create(T::class.java)
    }

     fun createObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE,false)
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        return objectMapper
    }
}