/*
* Copyright 2024
*/
package com.bitcoin.tipjar.feature.home

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized

data class HomeScreenState(
  val save: Async<Unit> = Uninitialized,
) : MavericksState
