package com.sakestores.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.sakestores.design_system.theme.SakePrimary
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.design_system.utils.StatusBarColor
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the Sake Stores Android application.
 *
 * This activity sets up the UI using Jetpack Compose, applying the app's theme,
 * customizing the status bar color, and rendering the root [SakeStores] composable
 * within a [Scaffold] layout.
 *
 * The class is annotated with [AndroidEntryPoint], enabling dependency injection with Hilt.
 *
 * @constructor Creates the [MainActivity] instance.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is first created.
     *
     * This method initializes the Compose UI content using [setContent],
     * wraps the UI in [SakeStoresTheme], sets the status bar color using [StatusBarColor],
     * and displays the [SakeStores] screen within a [Scaffold] that respects system insets.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     * being shut down, this contains the most recent state. Otherwise, it is `null`.
     */
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