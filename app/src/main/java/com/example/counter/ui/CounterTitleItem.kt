package com.example.counter.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.counter.database.Counter

@Composable
fun CounterTitleItem(counter: Counter, clickableFun: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(MaterialTheme.colors.surface)
            .clickable {
                clickableFun()
            }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = counter.name,
                modifier = Modifier.weight(5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = counter.value.toString(),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        if (counter.target != null) {
            LinearProgressIndicator(
                progress = { counter.value.toFloat() / counter.target.toFloat() },
                color = MaterialTheme.colors.primary,
                trackColor = Color.Transparent,
                drawStopIndicator = {}
            )
        }
    }
}

@Composable
@Preview
fun TitlePreview1() {
    CounterTitleItem(Counter("Preview", 15)) { }
}

@Composable
@Preview
fun TitlePreview2() {
    CounterTitleItem(Counter("Preview", 15, 1, 45)) { }
}

@Composable
@Preview
fun TitlePreview3() {
    CounterTitleItem(Counter("Very long preview ".repeat(5), 15, 1, 45)) { }
}

