package com.example.counter.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

@Composable
fun CounterTheme(
    content: @Composable () -> Unit
) {
    val colors = Colors(
        primary = Color(0xFF007AFF),
        primaryVariant = Color(0xFF005BB5),
        secondary = Color(0xFF34C759),
        secondaryVariant = Color(0xFF1E8E3E),
        background = Color(0xFF000000),
        surface = Color(0xFF1C1C1E),
        error = Color(0xFFFF3B30),
        onPrimary = Color(0xFFFFFFFF),
        onSecondary = Color(0xFFFFFFFF),
        onBackground = Color(0xFFFFFFFF),
        onSurface = Color(0xFFFFFFFF),
        onSurfaceVariant = Color(0xFF8E8E93),
        onError = Color(0xFFFFFFFF)

    )
    MaterialTheme(
        colors = colors,
        content = content
    )
}