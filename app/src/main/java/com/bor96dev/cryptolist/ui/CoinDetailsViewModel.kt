package com.bor96dev.cryptolist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bor96dev.cryptolist.data.CoinRepository
import com.bor96dev.cryptolist.data.Result
import com.bor96dev.cryptolist.domain.CoinDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {
    private val _state = MutableStateFlow<CoinDetailsState>(CoinDetailsState.Loading)
    val state: StateFlow<CoinDetailsState> = _state.asStateFlow()

    fun loadCoinDetails(coinId: String) {
        viewModelScope.launch {
            _state.value = CoinDetailsState.Loading
            when (val result = repository.getCoinDetails(coinId)) {
                is Result.Success -> _state.value = CoinDetailsState.Success(result.data)
                is Result.Failure -> _state.value =
                    CoinDetailsState.Error(result.exception.message ?: "Error")
            }
        }
    }
}

sealed class CoinDetailsState {
    object Loading : CoinDetailsState()
    data class Success(val coin: CoinDetails) : CoinDetailsState()
    data class Error(val message: String) : CoinDetailsState()
}