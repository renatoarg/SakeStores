package com.sakestores.design_system.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Dark color scheme for the Sake Stores design system.
 *
 * Defines the color palette used when the system is in dark theme mode,
 * including primary, secondary, tertiary colors, background, surface, and their corresponding "on" colors.
 */
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

/**
 * Light color scheme for the Sake Stores design system.
 *
 * Defines the color palette used when the system is in light theme mode,
 * including primary, secondary, tertiary colors, background, surface, and their corresponding "on" colors.
 */
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

/**
 * Composable function that applies the Sake Stores theme to its content.
 *
 * Selects either the dark or light color scheme based on the [darkTheme] parameter
 * or system setting, and applies the color scheme, typography, and shapes to the MaterialTheme.
 *
 * @param darkTheme Optional boolean to override the system dark theme setting. Defaults to system setting.
 * @param content The composable content to which the theme will be applied.
 */
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
