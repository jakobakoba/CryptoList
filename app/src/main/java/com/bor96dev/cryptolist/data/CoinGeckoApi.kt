package com.bor96dev.cryptolist.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {
    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") currency: String,
        @Query("per_page") perPage: Int = 30,
        @Query("page") page: Int = 1
    ): List<CoinMarket>

    @GET("coins/{id}")
    suspend fun getCoinDetails(
        @Path("id") id: String
    ): CoinDetails
}