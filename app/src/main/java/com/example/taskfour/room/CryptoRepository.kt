package com.example.taskfour.room

import androidx.lifecycle.LiveData
import com.example.taskfour.model.CryptoModel

class CryptoRepository(private val cryptoDao: CryptoDao) {
    val readAllData: LiveData<List<CryptoModel>> = cryptoDao.getAllCryptos()
    suspend fun insertCrypto(crypto: CryptoModel) {
        cryptoDao.insertCrypto(crypto)
    }
    suspend fun updateCrypto(crypto: CryptoModel) {
        cryptoDao.updateCrypto(crypto)
    }

    suspend fun deleteById(coinId: Int){
        cryptoDao.deleteCryptoById(coinId)
    }
    fun isCoinInDatabase(coinId: Int):LiveData<Boolean>{
        return cryptoDao.isCoinInDatabase(coinId)
    }

    fun getAllCrypto(): LiveData<List<CryptoModel>> {
        return cryptoDao.getAllCryptos()
    }
}