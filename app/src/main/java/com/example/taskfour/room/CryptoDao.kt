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

    @Query("SELECT * FROM crypto_table")
    suspend fun getAllCrypto(): List<CryptoModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCrypto(crypto: CryptoModel)

    @Query("DELETE FROM crypto_table WHERE symbol = :symbol")
    suspend fun deleteCryptoBySymbol(symbol: String)

    @Query("DELETE FROM crypto_table")
    suspend fun deleteAllCryptos()

    @Query("SELECT * FROM crypto_table WHERE symbol = :symbol")
    fun getCryptoBySymbol(symbol: String): LiveData<CryptoModel?>

    @Query("SELECT EXISTS(SELECT 1 FROM crypto_table WHERE symbol = :symbol LIMIT 1)")
    fun isSymbolInDatabase(symbol: String): LiveData<Boolean>
}
