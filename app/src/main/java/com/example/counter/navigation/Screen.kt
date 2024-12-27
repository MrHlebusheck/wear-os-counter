package com.example.counter.navigation

const val COUNTER_ARG_ID = "id"
const val DELETE_ARG_ID = "id"

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Counter : Screen("counter/{$COUNTER_ARG_ID}") {
        fun passId(id: Int): String {
            return route.replace("{$COUNTER_ARG_ID}", id.toString())
        }
    }

    object Delete : Screen("delete/{$DELETE_ARG_ID}") {
        fun passId(id: Int): String {
            return route.replace("{$DELETE_ARG_ID}", id.toString())
        }
    }
}