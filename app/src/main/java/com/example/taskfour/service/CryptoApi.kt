package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import com.example.taskfour.utilies.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {

    @GET(Constants.END_POINT_COIN)
    suspend fun getList(): List<CryptoModel>

    @GET("coins/markets")
    suspend fun getListCoin(
        @Query("vs_currency") currency: String = "usd",
        @Query("per_page") perPage: Int = 250,
        @Query("page") page: String
    ): List<CryptoModel>
}