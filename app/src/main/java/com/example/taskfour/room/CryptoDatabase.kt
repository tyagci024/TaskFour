package com.example.taskfour.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskfour.model.CryptoModel

@Database(entities = [CryptoModel::class], version = 8, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao
}