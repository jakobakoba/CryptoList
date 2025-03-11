package com.bor96dev.cryptolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bor96dev.cryptolist.ui.theme.CryptoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoListTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "coin_list"
                ) {
                    composable("coin_list") {
                        CoinListScreen(
                            viewModel = hiltViewModel(),
                            onCoinClick = { coinId ->
                                navController.navigate("coin_details/$coinId")
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    composable("coin_details{coinId}") { backStackEntry ->
                        val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
                        CoinDetailsScreen(
                            coinId = coinId,
                            viewModel = hiltViewModel(),
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

