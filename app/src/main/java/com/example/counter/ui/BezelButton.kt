package com.example.counter.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme

@Composable
fun BezelButton(
    active: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (active) MaterialTheme.colors.primary else MaterialTheme.colors.onSurfaceVariant,
        label = "Bezel background"
    )

    RoundedButton(
        text = if (active) "Bezel On" else "Bezel Off",
        modifier = Modifier
            .width(100.dp)
            .height(24.dp),
        btnColors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ) {
        onClick()
    }
}

@Composable
@Preview
fun BezelButtonPreviewActive() {
    BezelButton(true) { }
}

@Composable
@Preview
fun BezelButtonPreviewInactive() {
    BezelButton(false) { }
}
