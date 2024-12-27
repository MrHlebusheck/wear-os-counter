package com.example.counter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.TimeText
import com.example.counter.database.CounterDatabase
import com.example.counter.database.CounterRepository
import com.example.counter.database.CounterViewModel
import com.example.counter.database.CounterViewModelFactory
import com.example.counter.navigation.setupNavGraph
import com.example.counter.theme.CounterTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    lateinit var database: CounterDatabase
    lateinit var repository: CounterRepository
    lateinit var factory: CounterViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        database = CounterDatabase.getDatabase(applicationContext)
        repository = CounterRepository(database.counterDao())
        factory = CounterViewModelFactory(repository)

        setContent {
            CounterTheme {
                navController = rememberNavController()
                val counterViewModel: CounterViewModel = viewModel(factory = factory)

                TimeText()
                setupNavGraph(navController, counterViewModel)
            }
        }
    }
}
