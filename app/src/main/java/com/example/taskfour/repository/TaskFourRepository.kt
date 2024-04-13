package com.example.taskfour.repository

import com.example.taskfour.service.CryptoApi
import com.example.taskfour.service.NewsApi
import javax.inject.Inject

class TaskFourRepository @Inject constructor(private val api: CryptoApi,private val apiNews:NewsApi) {
    suspend fun getAllCoin() = api.getList()
    fun getAllNews() = apiNews.getAllNews()
    }