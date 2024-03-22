package com.example.taskfour.service

import com.example.taskfour.model.CryptoModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CryptoApiService {
    companion object {
        const val URL = "https://api.coingecko.com/api/v3/"
    }

    private val BASE_URL = URL
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoApi::class.java)

    suspend fun getCoinList(): List<CryptoModel> {// list isim
        return api.getList()
    }
}
