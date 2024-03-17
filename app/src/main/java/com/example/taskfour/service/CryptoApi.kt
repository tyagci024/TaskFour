package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {

    @GET("coins/markets?vs_currency=usd&per_page=250&page=1")
    suspend fun getList(): List<CryptoModel>
}