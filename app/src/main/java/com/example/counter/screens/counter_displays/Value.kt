package com.example.counter.screens.counter_displays

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.counter.database.Counter
import com.example.counter.screens.ValueRotaryFunction
import com.example.counter.ui.BezelButton
import com.example.counter.ui.CounterValue
import com.example.counter.ui.RoundedButton
import com.example.counter.ui.Title

@Composable
fun DisplayValue(
    counter: Counter?,
    activeRotaryFunction: Int,
    decrement: () -> Unit,
    increment: () -> Unit,
    toggleRotaryInput: () -> Unit,
) {
    if (counter == null) return

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .size(screenWidth, screenHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Title(counter.name, Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundedButton("-") { decrement() }
            CounterValue(counter.value, counter.target)
            RoundedButton("+") { increment() }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BezelButton(activeRotaryFunction == ValueRotaryFunction) { toggleRotaryInput() }
        }

    }
}


@Preview(device = "id:Samsung watch 6 classic", showBackground = true)
@Composable
fun ValuePreview() {
    DisplayValue(Counter("Preview"), 0, {}, {}, {})
}
