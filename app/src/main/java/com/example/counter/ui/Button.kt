package com.example.counter.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.counter.R

@Composable
private fun RoundedButton(
    modifier: Modifier = Modifier,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
    composable: @Composable () -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    val animatedScale by animateFloatAsState(scale)
    Button(
        onClick = onClick,
        modifier = Modifier
            .scale(animatedScale)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val down = event.changes.any { it.changedToDown() }
                        val up = event.changes.any { it.changedToUp() }
                        if (down) scale = 0.9f
                        if (up) scale = 1f
                    }
                }

            }
            .composed { modifier },
        colors = btnColors
    ) {
        composable()
    }
}

@Composable
fun RoundedButton(
    text: String,
    modifier: Modifier = Modifier,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    RoundedButton(
        modifier = modifier,
        onClick = onClick,
        btnColors = btnColors
    ) {
        Text(text)
    }
}

@Composable
fun RoundedButton(
    resource: Painter,
    description: String,
    modifier: Modifier = Modifier,
    btnColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    RoundedButton(
        modifier = modifier,
        onClick = onClick,
        btnColors = btnColors
    ) {
        Icon(
            painter = resource,
            description,
            modifier = Modifier.size(24.dp, 24.dp)
        )
    }
}


@Composable
@Preview
fun RoundedButtonPreviewWithText() {
    RoundedButton("Prev") {}
}

@Composable
@Preview
fun RoundedButtonPreviewWithIcon() {
    RoundedButton(
        painterResource(R.drawable.rounded_check_24),
        "yes",
    ) {}
}
