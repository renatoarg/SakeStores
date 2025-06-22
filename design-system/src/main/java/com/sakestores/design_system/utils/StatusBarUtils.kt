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
 * Configura a cor da status bar
 * @param color Cor da status bar (padrão: cor primária do tema)
 * @param darkIcons Se os ícones devem ser escuros (calculado automaticamente baseado na luminância)
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
 * Configura tanto a status bar quanto a navigation bar
 * @param statusBarColor Cor da status bar
 * @param navigationBarColor Cor da navigation bar
 * @param darkStatusBarIcons Se os ícones da status bar devem ser escuros
 * @param darkNavigationBarIcons Se os ícones da navigation bar devem ser escuros
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

            // Configurar cores
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            // Configurar ícones
            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = darkStatusBarIcons
                controller.isAppearanceLightNavigationBars = darkNavigationBarIcons
            }
        }
    }
}

/**
 * Torna a status bar transparente (para telas fullscreen)
 * @param darkIcons Se os ícones devem ser escuros
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
 * Aplica estilo padrão do Sake Stores para as barras do sistema
 * @param isDarkTheme Se está no tema escuro
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
 * Esconde a status bar (para telas verdadeiramente fullscreen)
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
 * Mostra a status bar (reverter HideStatusBar)
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
 * Aplica edge-to-edge com padding automático
 * Útil para telas que devem ir até as bordas da tela
 */
@Composable
fun EdgeToEdgeStatusBar() {
    val view = LocalView.current
    val context = LocalContext.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window

            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Status bar transparente
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Ícones escuros por padrão
            WindowCompat.getInsetsController(window, view).let { controller ->
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
    }
}