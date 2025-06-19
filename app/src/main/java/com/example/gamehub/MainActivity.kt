package com.example.gamehub

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.gamehub.ui.theme.GameHubTheme
import com.example.gamehub.ui.HomeScreen
import com.example.gamehub.ui.HomeViewModel
import com.example.gamehub.util.NetworkManager
import com.example.gamehub.di.AppContainer

//class MainActivity : ComponentActivity() {
//
//}


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as GameHubApp).container
        setContent {
            GameHubTheme {
                val viewModel: HomeViewModel by viewModels {
                    object : androidx.lifecycle.ViewModelProvider.Factory {
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return HomeViewModel(
                                NetworkManager(applicationContext),
                                container.interactor
                            ) as T
                        }
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomeScreen(viewModel)
                }
            }
        }
    }
}