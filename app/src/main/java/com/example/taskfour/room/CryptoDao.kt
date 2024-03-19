package com.example.taskfour.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskfour.model.CryptoModel

@Dao
interface CryptoDao {
    @Insert
    suspend fun insertCrypto(crypto: CryptoModel)

    @Query("SELECT * FROM crypto_table")
    fun getAllCryptos(): LiveData<List<CryptoModel>>
}