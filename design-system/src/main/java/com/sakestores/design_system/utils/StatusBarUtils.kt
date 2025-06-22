package com.sakestores.design_system.utils

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * Composable function to set the status bar color and icon appearance.
 *
 * @param color The color to set the status bar. Defaults to [MaterialTheme.colorScheme.primaryContainer].
 * @param darkIcons Whether the status bar icons should be dark. Defaults to true if [color] luminance is high.
 */
@Composable
fun StatusBarColor(
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    darkIcons: Boolean = color.luminance() > 0.5f
) {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            window.statusBarColor = color.toArgb()

            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = darkIcons
            }
        }
    }
}

/**
 * Composable function to set both status bar and navigation bar colors and icon appearances.
 *
 * @param statusBarColor Color for the status bar. Defaults to [MaterialTheme.colorScheme.primaryContainer].
 * @param navigationBarColor Color for the navigation bar. Defaults to [MaterialTheme.colorScheme.surface].
 * @param darkStatusBarIcons Whether status bar icons should be dark. Defaults based on [statusBarColor] luminance.
 * @param darkNavigationBarIcons Whether navigation bar icons should be dark. Defaults based on [navigationBarColor] luminance.
 */
@Composable
fun SystemBarColors(
    statusBarColor: Color = MaterialTheme.colorScheme.primaryContainer,
    navigationBarColor: Color = MaterialTheme.colorScheme.surface,
    darkStatusBarIcons: Boolean = statusBarColor.luminance() > 0.5f,
    darkNavigationBarIcons: Boolean = navigationBarColor.luminance() > 0.5f
) {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window

            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = darkStatusBarIcons
                controller.isAppearanceLightNavigationBars = darkNavigationBarIcons
            }
        }
    }
}

/**
 * Composable function to make the status bar transparent and optionally set icon color.
 *
 * @param darkIcons Whether the status bar icons should be dark. Defaults to true.
 */
@Composable
fun TransparentStatusBar(
    darkIcons: Boolean = true
) {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()

            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = darkIcons
            }
        }
    }
}

/**
 * Composable function to apply the SakeStores system bar style based on theme.
 *
 * @param isDarkTheme If true, applies dark theme colors; otherwise, applies light theme colors.
 */
@Composable
fun SakeSystemBars(
    isDarkTheme: Boolean = false
) {
    if (isDarkTheme) {
        SystemBarColors(
            statusBarColor = Color(0xFF1E1E1E),
            navigationBarColor = Color(0xFF121212),
            darkStatusBarIcons = false,
            darkNavigationBarIcons = false
        )
    } else {
        SystemBarColors(
            statusBarColor = MaterialTheme.colorScheme.primaryContainer,
            navigationBarColor = MaterialTheme.colorScheme.surface,
            darkStatusBarIcons = true,
            darkNavigationBarIcons = true
        )
    }
}

/**
 * Composable function to hide the status bar and allow transient system bars to be shown by swipe.
 */
@Composable
fun HideStatusBar() {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window

            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.hide(androidx.core.view.WindowInsetsCompat.Type.statusBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

/**
 * Composable function to show the status bar.
 */
@Composable
fun ShowStatusBar() {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window

            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.show(androidx.core.view.WindowInsetsCompat.Type.statusBars())
            }
        }
    }
}

/**
 * Composable function to enable edge-to-edge mode with transparent status and navigation bars,
 * and default dark icons for both bars.
 */
@Composable
fun EdgeToEdgeStatusBar() {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window

            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Set transparent status and navigation bars
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Default to dark icons on status and navigation bars
            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
    }
}
