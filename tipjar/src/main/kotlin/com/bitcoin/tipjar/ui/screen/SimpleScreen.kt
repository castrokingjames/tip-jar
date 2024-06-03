/*
* Copyright 2024
*/
@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitcoin.tipjar.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitcoin.tipjar.ui.compose.theme.Typography
import com.bitcoin.tipjar.ui.viewmodel.SimpleViewModel
import com.bitcoin.tipjar.ui.viewmodel.SimpleViewModel.Companion.simpleViewModel

@Composable
fun SampleScreen() {
  val viewModel: SimpleViewModel = simpleViewModel("John Doe")
  Scaffold(
    topBar = {
      Toolbar()
    },
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
    ) {
      Text(
        "Hello, ${viewModel.name}",
        style = Typography.regular,
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(16.dp),
      )
    }
  }
}

@Composable
private fun Toolbar() {
  Column {
    TopAppBar(
      title = {
        Text(
          text = "TipJar",
          color = MaterialTheme.colorScheme.onPrimary,
        )
      },
    )
    HorizontalDivider(
      thickness = 0.1.dp,
      color = MaterialTheme.colorScheme.primaryContainer,
    )
  }
}
