/*
* Copyright 2024
*/
package com.bitcoin.tipjar.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.mavericksViewModel
import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import com.bitcoin.tipjar.ui.viewmodel.AssistedViewModelFactory
import com.bitcoin.tipjar.ui.viewmodel.ViewModel
import com.bitcoin.tipjar.ui.viewmodel.viewModelFactory
import com.bitcoin.tipjar.usecase.SaveReceiptUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.error

class HomeScreenViewModel @AssistedInject constructor(
  @Assisted
  private val initialState: HomeScreenState,
  private val saveReceipt: SaveReceiptUseCase,
  @Dispatcher(Thread.MAIN)
  private val ui: CoroutineDispatcher,
  @Dispatcher(Thread.IO)
  private val io: CoroutineDispatcher,
) : ViewModel<HomeScreenState>(initialState, ui) {

  val amount = mutableStateOf<String?>(null)
  val count = mutableIntStateOf(1)
  val percentage = mutableStateOf<String?>(null)
  val tip = mutableDoubleStateOf(0.0)
  val totalTip = mutableDoubleStateOf(0.0)

  init {
    load()
  }

  private fun load() {
    onAsync(
      HomeScreenState::save,
      onFail = {
        delay(1000L)
        setState {
          copy(save = Uninitialized)
        }
      },
      onSuccess = {
        delay(1000L)
        amount.value = null
        count.value = 1
        percentage.value = null
        recalculate()
        setState {
          copy(save = Uninitialized)
        }
      },
    )
  }

  fun setAmount(text: String) {
    // Limit to 10 digit long
    if (text.length > 10) {
      return
    } else if (text.isEmpty()) {
      amount.value = text
    } else {
      val number = text.toDoubleOrNull()
      amount.value = when (number) {
        null -> amount.value
        else -> text
      }
    }
    recalculate()
  }

  fun setCount(number: Int) {
    // Minimum count 1
    if (count.value <= 1 && number == -1) {
      return
    }
    count.value += number
    recalculate()
  }

  fun setPercentage(text: String) {
    if (text.isDigitsOnly()) {
      val number = text.toIntOrNull()
      percentage.value = when (number) {
        null -> ""
        // Limit to 100%
        in 100..Int.MAX_VALUE -> "100"
        else -> "$number"
      }
    }
    recalculate()
  }

  private fun recalculate() {
    val amount = amount
      .value
      ?.toDoubleOrNull() ?: 0.0
    val count = count
      .value
    val percentage = percentage
      .value
      ?.toIntOrNull() ?: 0

    totalTip.value = (percentage / 100.0) * amount
    tip.value = totalTip.value / count
  }

  fun save(path: String? = null) {
    val context = CoroutineExceptionHandler { _, exception ->
      setState {
        copy(
          save = Fail(exception),
        )
      }
      Timber.error { "HomeScreenViewModel Error: $exception" }
    } + io
    launch(context) {
      val amount = amount
        .value
        ?.toDoubleOrNull() ?: 0.0
      val tip = tip.value
      if (amount == 0.0 || tip == 0.0) {
        setState {
          copy(
            save = Fail(Exception("Amount or Tip must not be zero")),
          )
        }
      } else {
        saveReceipt(amount, tip, path)
        setState {
          copy(
            save = Success(Unit),
          )
        }
      }
    }
  }

  @AssistedFactory
  interface Factory : AssistedViewModelFactory<HomeScreenViewModel, HomeScreenState> {
    override fun create(state: HomeScreenState): HomeScreenViewModel
  }

  companion object :
    MavericksViewModelFactory<HomeScreenViewModel, HomeScreenState> by viewModelFactory() {

    @Composable
    fun homeScreenViewModel(): HomeScreenViewModel = mavericksViewModel(
      keyFactory = {
        "HomeScreenViewModel"
      },
      argsFactory = {
        HomeScreenArgs
      },
    )
  }
}
