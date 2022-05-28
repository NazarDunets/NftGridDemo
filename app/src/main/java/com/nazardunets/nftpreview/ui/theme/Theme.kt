package com.nazardunets.nftpreview.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun NFTPreviewTheme(content: @Composable () -> Unit) {
    val colors = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200,
        background = Color.Black
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}