package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import com.example.taskfour.model.NewsItem
import com.example.taskfour.utilies.Constants.END_POINT_COIN
import com.example.taskfour.utilies.Constants.END_POINT_NEWS
import io.reactivex.Single
import retrofit2.http.GET

interface Apis {
    @GET(END_POINT_COIN)
    suspend fun getList(): List<CryptoModel>

    @GET(END_POINT_NEWS)
    fun getAllNews(): Single<List<NewsItem>>
}