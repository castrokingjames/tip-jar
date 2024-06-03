/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.viewmodel

import android.os.Parcelable
import androidx.compose.runtime.Composable
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.compose.mavericksViewModel
import com.airbnb.mvrx.withState
import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.parcelize.Parcelize

class SimpleViewModel @AssistedInject constructor(
  @Assisted
  private val initialState: SimpleScreenState,
  @Dispatcher(Thread.MAIN)
  private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel<SimpleViewModel.SimpleScreenState>(initialState, coroutineDispatcher) {

  val name: String
    get() {
      return withState(this) { state ->
        state.name
      }
    }

  data class SimpleScreenState(val name: String) : MavericksState {

    constructor(args: SimpleScreenArgs) : this(args.name)
  }

  @AssistedFactory
  interface Factory : AssistedViewModelFactory<SimpleViewModel, SimpleScreenState> {
    override fun create(state: SimpleScreenState): SimpleViewModel
  }

  @Parcelize
  data class SimpleScreenArgs(val name: String) : Parcelable

  companion object : MavericksViewModelFactory<SimpleViewModel, SimpleScreenState> by viewModelFactory() {

    @Composable
    fun simpleViewModel(name: String): SimpleViewModel = mavericksViewModel(
      keyFactory = {
        "SimpleViewModel"
      },
      argsFactory = {
        SimpleScreenArgs(name)
      },
    )
  }
}
