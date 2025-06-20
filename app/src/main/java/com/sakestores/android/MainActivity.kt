package com.sakestores.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sakestores.android.ui.navigation.SakeStoresNavigation
import com.sakestores.android.ui.theme.SakeStoresTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SakeStoresTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SakeStoresNavigation()
                }
            }
        }
    }
}