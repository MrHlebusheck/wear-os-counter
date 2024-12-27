package com.example.counter.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.counter.R
import com.example.counter.database.Counter
import com.example.counter.database.CounterViewModel
import com.example.counter.navigation.Screen
import com.example.counter.ui.RoundedButton

@Composable
fun DeleteScreen(id: Int, navController: NavController, counterViewModel: CounterViewModel) {
    val counter by counterViewModel.counter(id).collectAsState(Counter("Error"))
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedButton(
            painterResource(R.drawable.rounded_close_24),
            "Not"
        ) { navController.popBackStack() }
        RoundedButton(painterResource(R.drawable.rounded_check_24), "Yes") {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
                counterViewModel.deleteCounter(counter)
            }
        }
    }
}