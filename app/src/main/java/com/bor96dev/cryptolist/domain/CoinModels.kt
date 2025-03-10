package com.bor96dev.cryptolist.domain

import android.media.Image
import com.google.gson.annotations.SerializedName

data class CoinMarket(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("current_price") val current_price: Double
)

data class CoinDetails(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: Image,
    @SerializedName("description") val description: Description,
    @SerializedName("categories") val categories: List<String>?
)

data class Image(
    @SerializedName("large") val large: String
)

data class Description(
    @SerializedName("en") val en: String?
)
