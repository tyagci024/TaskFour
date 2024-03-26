package com.example.taskfour.model

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("id") val id: String,
    @SerializedName("searchKeyWords") val searchKeyWords: List<String>,
    @SerializedName("feedDate") val feedDate: Long,
    @SerializedName("source") val source: String,
    @SerializedName("title") val title: String,
    @SerializedName("sourceLink") val sourceLink: String,
    @SerializedName("isFeatured") val isFeatured: Boolean,
    @SerializedName("imgUrl") val imgUrl: String?,
    @SerializedName("shareURL") val shareURL: String,
    @SerializedName("relatedCoins") val relatedCoins: List<String>,
    @SerializedName("content") val hasContent: Boolean,
    @SerializedName("link") val link: String?,
    @SerializedName("bigImg") val hasBigImage: Boolean)