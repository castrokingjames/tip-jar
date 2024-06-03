/*
* Copyright 2024
*/
package com.bitcoin.tipjar.feature.history.receipt

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bitcoin.tipjar.model.Receipt
import com.bitcoin.tipjar.ui.compose.theme.Typography
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReceiptView(
  receipt: Receipt,
  onReceiptSelected: (Receipt) -> Unit,
) {
  val date = SimpleDateFormat("yyyy MMMM dd")
    .format(
      Date(receipt.createdAt),
    )
  val amount = receipt.amount
  val tip = receipt.tip
  val path = receipt.path
  Box(
    modifier = Modifier
      .clickable(path != null) {
        onReceiptSelected(receipt)
      },
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
    ) {
      Column(
        modifier = Modifier.align(Alignment.CenterStart),
      ) {
        Text(
          text = "$date",
          style = Typography.regular,
          color = MaterialTheme
            .colorScheme
            .onPrimary,
        )
        Spacer(
          modifier = Modifier.size(12.dp),
        )
        Row {
          Text(
            text = "$${String.format("%.2f", amount)}",
            style = Typography.regular24,
            color = MaterialTheme
              .colorScheme
              .onPrimary,
          )
          Spacer(
            modifier = Modifier.size(22.dp),
          )
          Text(
            text = "$${String.format("%.2f", tip)}",
            style = Typography.regular,
            color = MaterialTheme
              .colorScheme
              .onSecondary,
            modifier = Modifier.align(Alignment.Bottom),
          )
        }
      }
      Box(
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .size(52.dp),
      ) {
        if (path != null) {
          val externalCacheDir = LocalContext
            .current
            .externalCacheDir
          val file = File(externalCacheDir, path)
          Image(
            painter = rememberAsyncImagePainter(file),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
          )
        }
      }
    }
  }
}
