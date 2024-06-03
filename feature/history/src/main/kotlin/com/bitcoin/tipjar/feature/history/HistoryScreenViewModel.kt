/*
* Copyright 2024
*/
package com.bitcoin.tipjar.feature.history

import androidx.compose.runtime.Composable
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.mavericksViewModel
import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import com.bitcoin.tipjar.ui.viewmodel.AssistedViewModelFactory
import com.bitcoin.tipjar.ui.viewmodel.ViewModel
import com.bitcoin.tipjar.ui.viewmodel.viewModelFactory
import com.bitcoin.tipjar.usecase.LoadReceiptsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import timber.log.error

class HistoryScreenViewModel @AssistedInject constructor(
  @Assisted
  private val initialState: HistoryScreenState,
  private val loadReceipts: LoadReceiptsUseCase,
  @Dispatcher(Thread.MAIN)
  private val ui: CoroutineDispatcher,
  @Dispatcher(Thread.IO)
  private val io: CoroutineDispatcher,
) : ViewModel<HistoryScreenState>(initialState, ui) {

  init {
    load()
  }

  private fun load() {
    val context = CoroutineExceptionHandler { _, exception ->
      setState {
        copy(
          receipts = Fail(exception),
        )
      }
      Timber.error { "HistoryScreenViewModel Error: $exception" }
    } + io
    launch(context) {
      setState {
        copy(receipts = Loading())
      }
      loadReceipts()
        .collectLatest { receipts ->
          setState {
            copy(receipts = Success(receipts))
          }
        }
    }
  }

  @AssistedFactory
  interface Factory : AssistedViewModelFactory<HistoryScreenViewModel, HistoryScreenState> {
    override fun create(state: HistoryScreenState): HistoryScreenViewModel
  }

  companion object :
    MavericksViewModelFactory<HistoryScreenViewModel, HistoryScreenState> by viewModelFactory() {

    @Composable
    fun historyScreenViewModel(): HistoryScreenViewModel = mavericksViewModel(
      keyFactory = {
        "HistoryScreenViewModel"
      },
      argsFactory = {
        HistoryScreenArgs
      },
    )
  }
}
