package com.example.flocarencius.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Define your custom colors
val MintGreens= Color(0xFF6EB589)
val MintGreensDarks = Color(0xFF5A9471)
val MintGreensLights = Color(0xFF8FCCA3)
val DarkGreens = Color(0xFF001909)

// Define dark and light color schemes
private val DarkColorScheme = darkColorScheme(
    primary = MintGreens,
    secondary = MintGreensLights,
    background = DarkGreens,
    surface = DarkGreens,
    onPrimary = DarkGreens,
    onSecondary = DarkGreens,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = MintGreens,
    secondary = MintGreensLights,
    background = DarkGreens,
    surface = DarkGreens,
    onPrimary = DarkGreens,
    onSecondary = DarkGreens,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun COPHGUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CustomTypography, // Define your Typography object accordingly
        shapes = AppShapes,         // Define your Shapes object accordingly
        content = content
    )
}
