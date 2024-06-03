/*
* Copyright 2024
*/
package com.bitcoin.tipjar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bitcoin.tipjar.ui.TipJarApp
import com.bitcoin.tipjar.ui.compose.theme.TipJarTheme

class RootActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TipJarTheme {
        TipJarApp()
      }
    }
  }
}
