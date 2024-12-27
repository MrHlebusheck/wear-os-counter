package com.example.counter.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text


@Composable
fun CounterValue(value: Int, target: Int? = null) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = Modifier
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedValue(
            value,
            modifier = Modifier.onSizeChanged {
                size = it
            },
            numberColor = if (value == target) MaterialTheme.colors.secondary
            else if (target!=null && value > target) MaterialTheme.colors.error
            else MaterialTheme.colors.onPrimary
        )
        if (target != null) {
            HorizontalDivider(
                color = MaterialTheme.colors.primary,
                thickness = 1.dp,
                modifier = Modifier
                    .animateContentSize()
                    .then(
                        with(LocalDensity.current) {
                            Modifier.width(size.width.toDp())
                        }
                    )
            )
            Text(target.toString(), style = MaterialTheme.typography.caption1)
        }

    }
}


@Preview(device = "id:Samsung watch 6 classic", showBackground = true)
@Composable
fun CounterValuePreviewWithTarget() {
    CounterValue(15, 38)
}

@Preview(device = "id:Samsung watch 6 classic", showBackground = true)
@Composable
fun CounterValuePreviewWithoutTarget() {
    CounterValue(15)
}
