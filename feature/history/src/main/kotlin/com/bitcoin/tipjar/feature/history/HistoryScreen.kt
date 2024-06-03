/*
* Copyright 2024
*/
@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitcoin.tipjar.feature.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bitcoin.tipjar.feature.history.HistoryScreenViewModel.Companion.historyScreenViewModel
import com.bitcoin.tipjar.feature.history.receipt.ReceiptView
import com.bitcoin.tipjar.model.Receipt
import com.bitcoin.tipjar.ui.R
import com.bitcoin.tipjar.ui.compose.component.LoadingView
import com.bitcoin.tipjar.ui.compose.theme.Typography
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HistoryScreen(
  onBackNavigationSelected: () -> Unit,
) {
  val viewModel: HistoryScreenViewModel = historyScreenViewModel()
  val state = viewModel.collectAsState().value
  val receiptsState = state.receipts
  val showReceipt = remember {
    mutableStateOf<Receipt?>(null)
  }
  if (showReceipt.value != null) {
    Dialog(
      onDismissRequest = {
        showReceipt.value = null
      },
    ) {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = Color.Transparent,
        ),
      ) {
        val receipt = showReceipt?.value ?: return@Card
        val date = SimpleDateFormat("yyyy MMMM dd")
          .format(
            Date(receipt.createdAt),
          )
        val amount = receipt.amount
        val tip = receipt.tip
        val path = receipt.path
        val externalCacheDir = LocalContext
          .current
          .externalCacheDir
        val file = File(externalCacheDir, path)
        Column {
          Image(
            painter = rememberAsyncImagePainter(file),
            contentDescription = null,
            modifier = Modifier
              .fillMaxWidth()
              .height(400.dp),
          )
          Card(
            colors = CardDefaults
              .cardColors(
                containerColor = MaterialTheme
                  .colorScheme
                  .primary,
              ),
          ) {
            Box(
              modifier = Modifier.padding(16.dp),
            ) {
              Column {
                Text(
                  text = "$date",
                  style = Typography.regular,
                  color = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(
                  modifier = Modifier.size(12.dp),
                )
                Box(
                  modifier = Modifier.fillMaxWidth(),
                ) {
                  Text(
                    text = "$${String.format("%.2f", amount)}",
                    style = Typography.regular24,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.BottomStart),
                  )
                  Text(
                    text = stringResource(R.string.tip_x).format(tip),
                    style = Typography.regular,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.BottomEnd),
                  )
                }
              }
            }
          }
        }
      }
    }
  }

  Scaffold(
    topBar = {
      Toolbar(onBackNavigationSelected)
    },
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
    ) {
      if (receiptsState is Loading) {
        LoadingView(
          modifier = Modifier.align(Alignment.Center),
        )
      } else if (receiptsState is Success) {
        val receipts = receiptsState.invoke()
        LazyColumn {
          receipts(receipts) { receipt ->
            showReceipt.value = receipt
          }
        }
      }
    }
  }
}

@Composable
private fun Toolbar(
  onBackNavigationSelected: () -> Unit,
) {
  Column {
    CenterAlignedTopAppBar(
      title = {
        Text(
          text = stringResource(R.string.saved_payments),
          style = Typography.regular,
          color = MaterialTheme
            .colorScheme
            .onPrimary,
        )
      },
      navigationIcon = {
        IconButton(
          onClick = onBackNavigationSelected,
        ) {
          Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            "KeyboardArrowLeft",
            tint = MaterialTheme
              .colorScheme
              .onPrimary,
          )
        }
      },
    )
    HorizontalDivider(
      thickness = 0.5.dp,
      color = MaterialTheme.colorScheme.onPrimaryContainer,
    )
  }
}

private fun LazyListScope.receipts(
  receipts: List<Receipt>,
  onReceiptSelected: (Receipt) -> Unit,
) {
  val count = receipts.size
  items(
    count = count,
    key = { index ->
      receipts[index].id
    },
    contentType = { index ->
      receipts[index].javaClass
    },
  ) { index ->
    val receipt = receipts[index]
    ReceiptView(receipt, onReceiptSelected)
  }
}
