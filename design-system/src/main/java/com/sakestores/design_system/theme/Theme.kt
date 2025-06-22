package com.sakestores.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SakePrimaryLight,
    secondary = SakeSecondaryLight,
    tertiary = SakeTertiaryLight,
    background = SakeNeutral900,
    surface = SakeNeutral800,
    onPrimary = Color.White,
    onSecondary = SakeNeutral900,
    onTertiary = Color.White,
    onBackground = SakeNeutral100,
    onSurface = SakeNeutral100,
    primaryContainer = SakePrimaryDark,
    onPrimaryContainer = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = SakePrimary,
    secondary = SakeSecondary,
    tertiary = SakeTertiary,
    background = SakeBackground,
    surface = SakeSurface,
    onPrimary = Color.White,
    onSecondary = SakeNeutral900,
    onTertiary = Color.White,
    onBackground = SakeNeutral800,
    onSurface = SakeNeutral800,

    primaryContainer = SakeStatusBarLight,
    onPrimaryContainer = SakeNeutral800,
    secondaryContainer = SakeSurfaceVariant,
    onSecondaryContainer = SakeNeutral700,

    error = SakeError,
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410E0B)
)

@Composable
fun SakeStoresTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SakeTypography,
        shapes = SakeShapes,
        content = content
    )
}