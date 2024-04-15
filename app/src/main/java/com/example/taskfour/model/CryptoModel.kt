package com.example.taskfour.model

import android.health.connect.datatypes.units.Percentage
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "crypto_table")
data class CryptoModel(
    @PrimaryKey(autoGenerate = true) var coinId: Int,
    @SerializedName("id") var id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("price_change_percentage_24h") val priceChangePercentage24H: Double,
    @SerializedName("high_24h") val high24h: Double,
    @SerializedName("low_24h") val low24h: Double,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("current_price") val currentPrice: Double,
    var fav: Boolean = false) : Parcelable
