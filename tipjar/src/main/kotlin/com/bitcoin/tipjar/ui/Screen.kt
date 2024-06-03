/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui

const val SCREEN_ID = "screen_id"

sealed class Screen(val route: String) {

  data object HomeScreen : Screen("home")

  data object HistoryScreen : Screen("history")
}
