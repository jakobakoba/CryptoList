package com.bor96dev.cryptolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.cryptolist.data.CoinRepository
import com.bor96dev.cryptolist.domain.CoinMarket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CoinRepository
): ViewModel() {
    private val _state = MutableStateFlow<CoinListState>(CoinListState.Loading)
    val state: StateFlow<CoinListState> = _state.asStateFlow()

    private var currentCurrency = "usd"

    init {
        loadCoins()
    }

    fun onCurrencyChanged(currency: String){
        currentCurrency = currency
        loadCoins()
    }

    private fun loadCoins(){
        viewModelScope.launch{
            _state.value = CoinListState.Loading
            when(val result = repository.getCoinMarkets(currentCurrency)){
                is Result.Success -> _state.value = CoinListState.Success(result.data)
                is Result.Failure -> state.value = CoinListState.Error(result.exception.message ?: "Error")
            }
        }
    }
}

sealed class CoinListState {
    object Loading: CoinListState()
    data class Success(val coins: List<CoinMarket>) : CoinListState()
    data class Error(val message: String): CoinListState()
}