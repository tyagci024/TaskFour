package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import com.example.taskfour.utilies.Constants
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoApi {

    @GET(Constants.END_POINT_COIN)
    suspend fun getList(): List<CryptoModel>

    @GET("coins/markets?vs_currency=usd&per_page=250&page={page}")
    suspend fun getListCoin(@Path("page") page: String): List<CryptoModel>
}