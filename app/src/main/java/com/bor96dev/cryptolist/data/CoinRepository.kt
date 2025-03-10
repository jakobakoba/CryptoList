package com.bor96dev.cryptolist.data

import com.bor96dev.cryptolist.domain.CoinDetails
import com.bor96dev.cryptolist.domain.CoinMarket
import javax.inject.Inject

class CoinRepository @Inject constructor(
    private val api: CoinGeckoApi
){
    suspend fun getCoinMarkets(currency: String): Result<List<CoinMarket>> {
        return try{
            Result.success(api.getCoinMarkets(currency))
        } catch(e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getCoinDetails(id: String): Result<CoinDetails> {
        return try {
            Result.success(api.getCoinDetails(id))
        } catch(e: Exception) {
            Result.failure(e)
        }
    }

}