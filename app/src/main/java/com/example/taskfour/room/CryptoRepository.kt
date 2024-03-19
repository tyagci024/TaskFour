package com.example.taskfour.room

import androidx.lifecycle.LiveData
import com.example.taskfour.model.CryptoModel

class CryptoRepository(private val cryptoDao: CryptoDao) {
    val readAllData: LiveData<List<CryptoModel>> = cryptoDao.getAllCryptos()
     suspend fun insertCrypto(crypto: CryptoModel) {
        cryptoDao.insertCrypto(crypto)
    }
}
