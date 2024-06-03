/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.viewmodel

import com.airbnb.mvrx.MavericksViewModel
import javax.inject.Inject

class SimpleViewModelManager @Inject constructor(
  private val map: Map<Class<out MavericksViewModel<*>>, AssistedViewModelFactory<*, *>>,
) : ViewModelManager {

  override fun get(key: Class<out MavericksViewModel<*>>): AssistedViewModelFactory<*, *>? {
    return map[key]
  }
}
