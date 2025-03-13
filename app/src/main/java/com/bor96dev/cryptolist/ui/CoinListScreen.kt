package com.bor96dev.cryptolist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bor96dev.cryptolist.R
import com.bor96dev.cryptolist.domain.CoinMarket
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel(),
    onCoinClick: (String) -> Unit,
    modifier: Modifier
) {
    val state by viewModel.state.collectAsState()
    val currentCurrency by viewModel.currentCurrency.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text(text = stringResource(R.string.title_crypto))}
            )
        }
    ){ padding ->
        Column(modifier = Modifier.padding(padding)){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                FilterChip(
                    selected = currentCurrency == "usd",
                    onClick = {viewModel.onCurrencyChanged("usd")},
                    label = {Text("USD")}
                )
                FilterChip(
                    selected = currentCurrency == "rub",
                    onClick = {viewModel.onCurrencyChanged("rub")},
                    label = {Text("RUB")}
                )
            }

            when (val screenState = state) {
                is CoinListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                        ) {
                        CircularProgressIndicator()
                    }
                }
                is CoinListState.Success -> {
                    LazyColumn {
                        items(screenState.coins) { coin ->
                            CoinItem(
                                coin = coin,
                                currentCurrency = currentCurrency,
                                onClick = {onCoinClick(coin.id)}
                            )
                        }
                    }
                }
                is CoinListState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Error: ${(state as CoinListState.Error).message}")
                    }
                }
            }
        }
    }
}

@Composable
fun CoinItem(
    coin: CoinMarket,
    currentCurrency: String,
    onClick: () -> Unit,
) {

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = coin.image,
            contentDescription = coin.name,
            modifier = Modifier.size(40.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ){
            Text(
                text = coin.name,
            )
            Text(
                text = coin.symbol.uppercase(),
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.End
        ){
            val currencySymbol = if (currentCurrency == "usd") "$" else "₽"
            val formattedPrice = String.format(Locale.US, "%,.2f", coin.current_price)
            Text(
                text = "$currencySymbol $formattedPrice"
            )
            val priceChangeValue = coin.price_change
            val formattedChange = String.format(Locale.US, "%+.2f%%", priceChangeValue)
            Text(
                text = formattedChange,
                color = when {
                    priceChangeValue >= 0 -> Color.Green
                    priceChangeValue < 0 -> Color.Red
                    else -> Color.Black
                }
            )
        }
    }
}