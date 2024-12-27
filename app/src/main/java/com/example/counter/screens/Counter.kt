package com.example.counter.screens

import android.app.RemoteInput
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.example.counter.database.Counter
import com.example.counter.database.CounterViewModel
import com.example.counter.screens.counter_displays.DisplayOptions
import com.example.counter.screens.counter_displays.DisplayStep
import com.example.counter.screens.counter_displays.DisplayValue
import kotlinx.coroutines.launch

const val TARGET_KEY = "target"
const val RENAME_KEY = "name"

const val ScrollRotaryFunction = 0
const val ValueRotaryFunction = 1
const val StepRotaryFunction = 2

@Composable
fun CounterScreen(
    counterId: Int,
    navController: NavController,
    counterViewModel: CounterViewModel
) {
    val counter by counterViewModel.counter(counterId).collectAsState(Counter("error"))
    var activeRotaryFunction by remember { mutableIntStateOf(ScrollRotaryFunction) }
    val columnFocusRequester = remember { FocusRequester() }
    var scrollIndex by remember { mutableIntStateOf(0) }

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberScalingLazyListState()

    fun toggleRotaryFunction(f: Int) {
        activeRotaryFunction =
            if (activeRotaryFunction != f) f else ScrollRotaryFunction
    }

    fun increment() {
        counterViewModel.incrementCounter(counter)
    }

    fun decrement() {
        counterViewModel.decrementCounter(counter)
    }

    fun incrementStep() {
        counterViewModel.incrementStep(counter)
    }

    fun decrementStep() {
        counterViewModel.decrementStep(counter)
    }

    val targetLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.let { data ->
            val results: Bundle = RemoteInput.getResultsFromIntent(data)
            val newInputText: CharSequence? = results.getCharSequence(TARGET_KEY)
            val userInput = newInputText?.toString()?.toIntOrNull()
            counterViewModel.setTarget(counter, userInput)
        }
    }

    val renameLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.let { data ->
            val results: Bundle = RemoteInput.getResultsFromIntent(data)
            val newInputText: CharSequence? = results.getCharSequence(RENAME_KEY)
            val userInput = newInputText?.toString() ?: ""
            counterViewModel.renameCounter(counter, userInput)
        }
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            listState.scrollToItem(scrollIndex)
        }
        columnFocusRequester.requestFocus()
    }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                coroutineScope.launch {
                    when (activeRotaryFunction) {
                        ScrollRotaryFunction -> {
                            scrollIndex = if (it.verticalScrollPixels > 0) minOf(scrollIndex + 1, 2)
                            else maxOf(scrollIndex - 1, 0)
                            listState.animateScrollToItem(scrollIndex)
                        }

                        ValueRotaryFunction -> {
                            if (it.verticalScrollPixels > 0) increment()
                            else decrement()
                        }

                        StepRotaryFunction -> {
                            if (it.verticalScrollPixels > 0) incrementStep()
                            else decrementStep()
                        }
                    }
                }
                true
            }
            .focusRequester(columnFocusRequester)
            .focusable(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            DisplayValue(counter, activeRotaryFunction, { decrement() }, { increment() }, {
                toggleRotaryFunction(
                    ValueRotaryFunction
                )
            })
        }
        item {
            DisplayStep(counter, activeRotaryFunction, { decrementStep() }, { incrementStep() }, {
                toggleRotaryFunction(
                    StepRotaryFunction
                )
            })
        }
        item {
            DisplayOptions(counter, targetLauncher, renameLauncher, navController)
        }
    }
}