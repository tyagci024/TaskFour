package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoApiService {
    private val BASE_URL = "https://api.coingecko.com/api/v3/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApi::class.java)

    suspend fun getCoin(): List<CryptoModel> {
        return api.getList()
    }
}
