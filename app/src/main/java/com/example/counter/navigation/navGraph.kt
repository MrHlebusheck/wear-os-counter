package com.example.counter.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.counter.database.CounterViewModel
import com.example.counter.screens.CounterScreen
import com.example.counter.screens.DeleteScreen
import com.example.counter.screens.ListOfCountersScreen

typealias EnterAnimation = @JvmSuppressWildcards() (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)?
typealias ExitAnimation = AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?

@Composable
fun setupNavGraph(navController: NavHostController, counterViewModel: CounterViewModel) {

    val enterTransition: EnterAnimation =
        composable@{
            return@composable fadeIn(tween(1000))
        }
    val exitTransition: ExitAnimation = composable@{
        return@composable slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Start, tween(500)
        )
    }

    val popEnterTransition: EnterAnimation = composable@{
        return@composable slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.End, tween(500)
        )
    }

    NavHost(
        navController = navController, startDestination = Screen.Home.route,
    ) {
        composable(
            Screen.Home.route,
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition
        ) {
            ListOfCountersScreen(navController, counterViewModel)
        }

        composable(
            Screen.Counter.route,
            arguments = listOf(
                navArgument(COUNTER_ARG_ID) {
                    type = NavType.IntType
                },
            ),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition
        ) {
            val id = it.arguments?.getInt((COUNTER_ARG_ID))
            if (id != null) {
                CounterScreen(id, navController, counterViewModel)
            }
        }
        composable(
            Screen.Delete.route,
            arguments = listOf(navArgument(DELETE_ARG_ID) {
                type = NavType.IntType
            }),
            enterTransition = enterTransition,
            exitTransition = exitTransition,
            popEnterTransition = popEnterTransition
        ) {
            val id = it.arguments?.getInt(DELETE_ARG_ID)
            if (id != null) {
                DeleteScreen(id, navController, counterViewModel)
            }
        }
    }
}