package com.example.counter.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = Modifier
            .padding(top = 24.dp)
            .width(150.dp)
            .composed { modifier },
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center
    )
}