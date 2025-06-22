@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.sakestores.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sakestores.design_system.theme.SakePrimary
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.design_system.utils.StatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SakeStoresTheme {
                StatusBarColor(
                    color = SakePrimary,
                    darkIcons = false
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SakeStores(modifier = Modifier
                        .padding(innerPadding)
                    )
                }
            }
        }
    }
}