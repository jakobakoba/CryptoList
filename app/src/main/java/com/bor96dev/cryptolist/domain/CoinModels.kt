package com.bor96dev.cryptolist.domain

import com.google.gson.annotations.SerializedName

data class CoinMarket(
    @SerializedName("id") val id: String,
    @SerializedName("symbol") val symbol: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("current_price") val current_price: Double,
    @SerializedName("price_change_percentage_24h") val price_change: Double
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
