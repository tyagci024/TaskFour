package com.example.taskfour.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskfour.model.CryptoModel

@Dao
interface CryptoDao {
    @Insert
    suspend fun insertCrypto(crypto: CryptoModel)

    @Query("SELECT * FROM crypto_table")
    fun getAllCryptos(): LiveData<List<CryptoModel>>

    @Update
    suspend fun updateCrypto(crypto: CryptoModel)

    @Query("DELETE FROM crypto_table WHERE symbol = :symbol")
    suspend fun deleteCryptoBySymbol(symbol: String)

    @Query("DELETE FROM crypto_table")
    suspend fun deleteAllCryptos()

    @Query("SELECT * FROM crypto_table WHERE symbol = :symbol")
    suspend fun getCryptoBySymbol(symbol: String): CryptoModel?
}
