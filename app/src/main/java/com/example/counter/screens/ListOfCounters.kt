package com.example.counter.screens

import android.app.RemoteInput
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Text
import com.example.counter.database.CounterViewModel
import com.example.counter.functions.RemoteInputKeyboard
import com.example.counter.navigation.Screen
import com.example.counter.ui.CounterTitleItem
import com.example.counter.ui.RoundedButton
import kotlinx.coroutines.launch

const val NAME_KEY = "counterName"

@Composable
fun ListOfCountersScreen(navController: NavHostController, counterViewModel: CounterViewModel) {
    val counters by counterViewModel.allCounters.collectAsState(initial = emptyList())

    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    val listState = rememberScalingLazyListState()


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.let { data ->
            val results: Bundle = RemoteInput.getResultsFromIntent(data)
            val newInputText: CharSequence? = results.getCharSequence(NAME_KEY)
            val userInput = newInputText?.toString() ?: ""
            counterViewModel.createCounter(userInput)
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                coroutineScope.launch {
                    listState.animateScrollBy(it.verticalScrollPixels)
                }
                true
            }
            .focusRequester(focusRequester)
            .focusable(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item{
            Text("Welcome to Counters", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        }
        counters.forEach {
            item(key = it.id) {
                CounterTitleItem(it) { navController.navigate(Screen.Counter.passId(it.id)) }
            }
        }
        item {
            RoundedButton(
                "+",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) { RemoteInputKeyboard(launcher, "Enter name for counter", NAME_KEY) }
        }
    }
}