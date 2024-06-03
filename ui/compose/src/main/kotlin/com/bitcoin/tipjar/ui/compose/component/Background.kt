/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.compose.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Background(
  background: Color = MaterialTheme.colorScheme.background,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Surface(
    color = background,
    modifier = modifier.fillMaxSize(),
  ) {
    content()
  }
}
