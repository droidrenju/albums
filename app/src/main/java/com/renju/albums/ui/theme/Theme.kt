package com.example.loyverse.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.renju.albums.ui.theme.Typography

@Composable
fun LoyverseMusicAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
