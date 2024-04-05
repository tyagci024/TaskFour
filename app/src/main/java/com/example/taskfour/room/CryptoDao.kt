package com.example.taskfour.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskfour.model.CryptoModel

@Dao
interface CryptoDao {
    @Insert
    suspend fun insertCrypto(crypto: CryptoModel)

    @Query("SELECT * FROM crypto_table")
    fun getAllCryptos(): LiveData<List<CryptoModel>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCrypto(crypto: CryptoModel)

    @Query("DELETE FROM crypto_table WHERE coinId = :coinId")
    suspend fun deleteCryptoById(coinId: Int)

    @Query("DELETE FROM crypto_table")
    suspend fun deleteAllCryptos()


    @Query("SELECT EXISTS(SELECT 1 FROM crypto_table WHERE coinId = :coinId LIMIT 1)")
    fun isCoinInDatabase(coinId: Int): LiveData<Boolean>
}
