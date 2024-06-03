/*
* Copyright 2022
*/
package com.bitcoin.tipjar.initializer

import android.content.Context
import com.airbnb.mvrx.Mavericks
import javax.inject.Inject

class MavericksInitializer @Inject constructor(
  private val context: Context,
) : Initializer {

  override fun invoke() {
    Mavericks.initialize(context)
  }
}
