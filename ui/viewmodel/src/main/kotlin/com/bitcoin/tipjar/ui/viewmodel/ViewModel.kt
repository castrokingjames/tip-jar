/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.viewmodel

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class ViewModel<S : MavericksState> constructor(
  private val initialState: S,
  private val coroutineDispatcher: CoroutineDispatcher,
) : MavericksViewModel<S>(initialState), CoroutineScope {

  private val job = SupervisorJob()

  override val coroutineContext: CoroutineContext get() = coroutineDispatcher + job

  override fun onCleared() {
    super.onCleared()
    job.cancel()
  }
}
