/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui

const val SCREEN_ID = "screen_id"

sealed class Screen(val route: String) {

  data object DetailScreen : Screen("screen_id/{$SCREEN_ID}") {

    fun withId(screenId: String): String {
      return route.replace("{$SCREEN_ID}", screenId)
    }
  }

  data object SimpleScreen : Screen("simple")
}
