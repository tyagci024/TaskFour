package com.example.taskfour.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "crypto_table")
data class CryptoModel(
    @PrimaryKey val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("high_24h") val high24h: Double,
    @SerializedName("low_24h") val low24h: Double,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("current_price") val currentPrice: Double,
    var isFavorite: Boolean = false ) : Parcelable
