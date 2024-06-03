/*
* Copyright 2024
*/
package com.bitcoin.tipjar.feature.history

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.bitcoin.tipjar.model.Receipt

data class HistoryScreenState(
  val receipts: Async<List<Receipt>> = Uninitialized,
) : MavericksState
