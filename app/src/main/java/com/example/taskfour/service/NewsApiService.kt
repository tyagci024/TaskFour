package com.example.taskfour.service

import com.example.taskfour.model.NewsItem
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService
{
    private val BASE_URL="https://raw.githubusercontent.com/"
    val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(NewsApi::class.java)

    fun getNewsData(): Single<List<NewsItem>> {
        return api.getAllNews()
    }
}