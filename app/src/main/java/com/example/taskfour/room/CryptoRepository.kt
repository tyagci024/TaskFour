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
    fun getCryptoBySymbol(symbol: String): LiveData<CryptoModel?> {
        return cryptoDao.getCryptoBySymbol(symbol)
    }

    suspend fun deleteBySymbol(symbol: String){
        cryptoDao.deleteCryptoBySymbol(symbol)
    }
    fun isSymbolInDatabase(symbol: String):LiveData<Boolean>{
        return cryptoDao.isSymbolInDatabase(symbol)
    }

    suspend fun getAllCrypto(): List<CryptoModel> {
        return cryptoDao.getAllCrypto()
    }
}
