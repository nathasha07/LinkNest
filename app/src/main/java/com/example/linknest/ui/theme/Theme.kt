
package com.example.linknest.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AutumnLightColors = lightColorScheme(
    primary = BrickRed,
    onPrimary = IvorySurface,

    secondary = Olive,
    onSecondary = IvorySurface,

    tertiary = BurntOrange,
    onTertiary = IvorySurface,

    background = CreamBackground,
    onBackground = DarkBrown,

    surface = IvorySurface,
    onSurface = DarkBrown,

    surfaceVariant = GoldenWheat,
    onSurfaceVariant = DarkBrown,

    outline = SoftBorder
)

private val AutumnDarkColors = darkColorScheme(
    primary = GoldenWheat,
    onPrimary = DarkBrown,

    secondary = Olive,
    onSecondary = IvorySurface,

    tertiary = BurntOrange,
    onTertiary = IvorySurface,

    background = DarkBackground,
    onBackground = LightText,

    surface = DarkSurface,
    onSurface = LightText,

    surfaceVariant = Color(0xFF49372B),
    onSurfaceVariant = LightText,

    outline = MutedBrown
)

@Composable
fun LinkNestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            AutumnDarkColors
        } else {
            AutumnLightColors
        },
        typography = Typography,
        content = content
    )
}
