package com.example.taskfour.repository

import androidx.lifecycle.LiveData
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.room.CryptoDao
import com.example.taskfour.service.CryptoApi
import com.example.taskfour.service.NewsApi
import javax.inject.Inject

class TaskFourRepository @Inject constructor(
    private val api: CryptoApi,
    private val apiNews: NewsApi,
    private val cryptoDao: CryptoDao
) {
    suspend fun getAllCoin() = api.getList()
    fun getAllNews() = apiNews.getAllNews()
    suspend fun insertCrypto(crypto: CryptoModel) {
        cryptoDao.insertCrypto(crypto)
    }
    suspend fun updateCrypto(crypto: CryptoModel) {
        cryptoDao.updateCrypto(crypto)
    }

    suspend fun deleteById(coinId: Int){
        cryptoDao.deleteCryptoById(coinId)
    }
    fun isCoinInDatabase(coinId: Int): LiveData<Boolean> {
        return cryptoDao.isCoinInDatabase(coinId)
    }

    fun getAllCrypto(): LiveData<List<CryptoModel>> {
        return cryptoDao.getAllCryptos()
    }
}