package com.example.counter.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

private data class Digit(val digitChar: Char, val fullNumber: Int, val place: Int) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Digit -> digitChar == other.digitChar
            else -> super.equals(other)
        }
    }
}

private operator fun Digit.compareTo(other: Digit): Int {
    return fullNumber.compareTo(other.fullNumber)
}


@Composable
fun AnimatedValue(
    value: Int,
    modifier: Modifier = Modifier,
    numberColor: Color = MaterialTheme.colors.onPrimary
) {
    val numColor by animateColorAsState(numberColor)
    Row(
        modifier = Modifier
            .animateContentSize()
            .composed { modifier },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        value.toString()
            .mapIndexed { index, c -> Digit(c, value, index) }
            .forEach { digit ->
                AnimatedContent(
                    targetState = digit,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        } else {
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                        }
                    }
                ) { digit ->
                    Text(
                        "${digit.digitChar}",
                        style = MaterialTheme.typography.title1,
                        textAlign = TextAlign.Center,
                        color = numColor
                    )
                }
            }
    }
}
