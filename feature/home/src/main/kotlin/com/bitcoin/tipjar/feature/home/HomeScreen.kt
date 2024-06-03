/*
* Copyright 2024
*/
@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitcoin.tipjar.feature.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bitcoin.tipjar.feature.home.HomeScreenViewModel.Companion.homeScreenViewModel
import com.bitcoin.tipjar.ui.R
import com.bitcoin.tipjar.ui.R.string.total_tip
import com.bitcoin.tipjar.ui.compose.theme.Typography
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun HomeScreen(
  onHistoryClick: () -> Unit,
) {
  val context = LocalContext.current
  val viewModel: HomeScreenViewModel = homeScreenViewModel()
  val amount = viewModel.amount
  val count = viewModel.count
  val percentage = viewModel.percentage
  val tip = viewModel.tip
  val totalTip = viewModel.totalTip
  val state = viewModel
    .collectAsState()
    .value
  val save = state.save
  LaunchedEffect(save) {
    if (save is Fail) {
      Toast.makeText(
        context,
        save.error.message,
        Toast.LENGTH_SHORT,
      )
        .show()
    } else if (save is Success) {
      onHistoryClick()
    }
  }

  val file = createTempFile(context)
  val uri = FileProvider.getUriForFile(
    Objects.requireNonNull(context),
    context.packageName + ".provider",
    file,
  )

  var capturedImageUri = remember {
    mutableStateOf<Uri>(Uri.EMPTY)
  }

  val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
    capturedImageUri.value = uri
  }

  val permissionLauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission(),
  ) { value ->
    if (value) {
      Toast.makeText(context, R.string.permission_granted, Toast.LENGTH_SHORT).show()
      cameraLauncher.launch(uri)
    } else {
      Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_SHORT).show()
    }
  }

  LaunchedEffect(capturedImageUri.value) {
    val path = capturedImageUri.value.lastPathSegment
    if (path?.isNotEmpty() == true) {
      viewModel.save(path)
    }
  }

  val check = remember { mutableStateOf(false) }
  Scaffold(
    topBar = {
      Toolbar(onHistoryClick)
    },
  ) { padding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
    ) {
      Column {
        Spacer(
          modifier = Modifier.size(16.dp),
        )
        AmountView(amount = amount.value) { text ->
          viewModel.setAmount(text)
        }
        Spacer(
          modifier = Modifier.size(32.dp),
        )
        PeopleCountView(count = count.value) { number ->
          viewModel.setCount(number)
        }
        Spacer(
          modifier = Modifier.size(32.dp),
        )
        TipPercentageView(amount = percentage.value) { text ->
          viewModel.setPercentage(text)
        }
        Spacer(
          modifier = Modifier.size(32.dp),
        )
        TipDetailsView(
          totalTip = totalTip.value,
          tip = tip.value,
        )
      }
      Box(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(24.dp),
      ) {
        Column {
          Row {
            Checkbox(
              checked = check.value,
              colors = CheckboxDefaults.colors(
                checkmarkColor = MaterialTheme.colorScheme.onTertiary,
                checkedColor = MaterialTheme.colorScheme.tertiary,
                uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer,
              ),
              onCheckedChange = { value ->
                check.value = value
              },
              modifier = Modifier.align(Alignment.Bottom),
            )
            Spacer(
              modifier = Modifier.size(12.dp),
            )
            Text(
              stringResource(R.string.take_photo_of_receipt),
              style = Typography.regular,
              color = MaterialTheme.colorScheme.onPrimary,
              modifier = Modifier.align(Alignment.CenterVertically),
            )
          }
          Spacer(
            modifier = Modifier.size(8.dp),
          )
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.colorScheme.tertiary,
              disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.tertiary),
            onClick = {
              if (check.value) {
                val permissionCheckResult = ContextCompat.checkSelfPermission(
                  context,
                  Manifest.permission.CAMERA,
                )
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                  cameraLauncher.launch(uri)
                } else {
                  permissionLauncher.launch(Manifest.permission.CAMERA)
                }
              } else {
                viewModel.save()
              }
            },
          ) {
            Box(
              modifier = Modifier.fillMaxWidth(),
            ) {
              Text(
                modifier = Modifier
                  .padding(16.dp)
                  .align(Alignment.Center),
                text = stringResource(R.string.save_payment),
                style = Typography.medium,
                color = MaterialTheme.colorScheme.onTertiary,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun Toolbar(
  onHistoryClick: () -> Unit,
) {
  Column {
    CenterAlignedTopAppBar(
      title = {
        Image(
          painter = painterResource(R.drawable.ic_logo),
          contentDescription = "icon",
        )
      },
      actions = {
        IconButton(
          onClick = onHistoryClick,
        ) {
          Icon(
            painter = painterResource(R.drawable.ic_history_24dp),
            "History",
            tint = MaterialTheme.colorScheme.onSecondary,
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

@Composable
private fun AmountView(
  modifier: Modifier = Modifier,
  amount: String? = null,
  onAmountChanged: (String) -> Unit,
) {
  val text = remember { mutableStateOf<String?>(null) }
  text.value = amount
  Column(
    modifier = modifier.padding(
      start = 24.dp,
      end = 24.dp,
    ),
  ) {
    Text(
      stringResource(R.string.enter_amount),
      style = Typography.regular,
      color = MaterialTheme.colorScheme.onPrimary,
    )
    Spacer(
      modifier = Modifier.size(16.dp),
    )
    Card(
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
      elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
      modifier = Modifier.fillMaxWidth(),
    ) {
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .onFocusChanged { state ->
            if (state.isFocused) {
              if (text.value == null) {
                text.value = ""
              }
            } else if (!state.isFocused) {
              if (text.value == "") {
                text.value = null
              }
            }
          },
        leadingIcon = {
          Text(
            text = "$",
            style = Typography.regular24,
            color = MaterialTheme.colorScheme.onPrimary,
          )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        value = text.value ?: "",
        onValueChange = { value ->
          onAmountChanged(value)
        },
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent,
          cursorColor = MaterialTheme.colorScheme.onPrimary,
          focusedTextColor = MaterialTheme.colorScheme.onPrimary,
          unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
          focusedContainerColor = MaterialTheme.colorScheme.primary,
          unfocusedContainerColor = MaterialTheme.colorScheme.primary,
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = Typography
          .regular42
          .copy(
            textAlign = TextAlign.Center,
          ),
        placeholder = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = text.value ?: "100.00",
            style = Typography
              .regular42
              .copy(
                textAlign = TextAlign.Center,
              ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
          )
        },
      )
    }
  }
}

@Composable
private fun PeopleCountView(
  modifier: Modifier = Modifier,
  count: Int = 1,
  onAmountChanged: (Int) -> Unit,
) {
  Column(
    modifier = modifier.padding(
      start = 24.dp,
      end = 24.dp,
    ),
  ) {
    Text(
      stringResource(R.string.how_many_people_),
      style = Typography.regular,
      color = MaterialTheme.colorScheme.onPrimary,
    )
    Spacer(
      modifier = Modifier.size(16.dp),
    )
    Box(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Card(
        modifier = Modifier.align(Alignment.CenterStart),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        onClick = {
          onAmountChanged(1)
        },
      ) {
        Icon(
          modifier = Modifier.padding(16.dp, 16.dp),
          painter = painterResource(R.drawable.ic_add_24dp),
          contentDescription = "Add",
          tint = MaterialTheme.colorScheme.tertiary,
        )
      }

      Text(
        modifier = Modifier.align(Alignment.Center),
        text = "$count",
        style = Typography.regular42,
        color = MaterialTheme.colorScheme.onPrimary,
      )

      Card(
        modifier = Modifier.align(Alignment.CenterEnd),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        onClick = {
          onAmountChanged(-1)
        },
      ) {
        Icon(
          modifier = Modifier.padding(16.dp, 16.dp),
          painter = painterResource(R.drawable.ic_remove_24dp),
          contentDescription = "Minus",
          tint = MaterialTheme.colorScheme.tertiary,
        )
      }
    }
  }
}

@Composable
private fun TipPercentageView(
  modifier: Modifier = Modifier,
  amount: String? = null,
  onAmountChanged: (String) -> Unit,
) {
  val text = remember { mutableStateOf<String?>(null) }
  text.value = amount
  Column(
    modifier = modifier.padding(
      start = 24.dp,
      end = 24.dp,
    ),
  ) {
    Text(
      stringResource(R.string.percent_tip),
      style = Typography.regular,
      color = MaterialTheme.colorScheme.onPrimary,
    )
    Spacer(
      modifier = Modifier.size(16.dp),
    )
    Card(
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
      elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
      shape = RoundedCornerShape(16.dp),
      border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
      modifier = Modifier.fillMaxWidth(),
    ) {
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth()
          .onFocusChanged { state ->
            if (state.isFocused) {
              if (text.value == null) {
                text.value = ""
              }
            } else if (!state.isFocused) {
              if (text.value == "") {
                text.value = null
              }
            }
          },
        trailingIcon = {
          Text(
            text = "%",
            style = Typography.regular24,
            color = MaterialTheme.colorScheme.onPrimary,
          )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = text.value ?: "",
        onValueChange = { text ->
          onAmountChanged(text)
        },
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent,
          cursorColor = MaterialTheme.colorScheme.onPrimary,
          focusedTextColor = MaterialTheme.colorScheme.onPrimary,
          unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
          focusedContainerColor = MaterialTheme.colorScheme.primary,
          unfocusedContainerColor = MaterialTheme.colorScheme.primary,
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = Typography
          .regular42
          .copy(
            textAlign = TextAlign.Center,
          ),
        placeholder = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = text.value ?: "10",
            style = Typography
              .regular42
              .copy(
                textAlign = TextAlign.Center,
              ),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
          )
        },
      )
    }
  }
}

@Composable
private fun TipDetailsView(
  modifier: Modifier = Modifier,
  totalTip: Double = 0.0,
  tip: Double = 0.0,
) {
  Column(
    modifier = modifier.padding(
      start = 24.dp,
      end = 24.dp,
    ),
  ) {
    Box(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        modifier = Modifier.align(Alignment.CenterStart),
        text = stringResource(total_tip),
        style = Typography.regular,
        color = MaterialTheme.colorScheme.onPrimary,
      )
      Text(
        modifier = Modifier.align(Alignment.CenterEnd),
        text = "$${String.format("%.2f", totalTip)}",
        style = Typography.regular,
        color = MaterialTheme.colorScheme.onPrimary,
      )
    }
    Spacer(
      modifier = Modifier.size(16.dp),
    )
    Box(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Text(
        modifier = Modifier.align(Alignment.CenterStart),
        text = stringResource(R.string.per_person),
        style = Typography.regular24,
        color = MaterialTheme.colorScheme.onPrimary,
      )
      Text(
        modifier = Modifier.align(Alignment.CenterEnd),
        text = "$${String.format("%.2f", tip)}",
        style = Typography.regular24,
        color = MaterialTheme.colorScheme.onPrimary,
      )
    }
  }
}

private fun createTempFile(context: Context): File {
  val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
  val fileName = "JPEG_" + timestamp + "_"
  return File.createTempFile(
    fileName,
    ".jpg",
    context.externalCacheDir,
  )
}
