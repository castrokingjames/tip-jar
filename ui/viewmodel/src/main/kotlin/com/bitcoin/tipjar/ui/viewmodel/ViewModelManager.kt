/*
* Copyright 2022
*/
package com.bitcoin.tipjar.ui.viewmodel

import com.airbnb.mvrx.MavericksViewModel

interface ViewModelManager {

  fun get(key: Class<out MavericksViewModel<*>>): AssistedViewModelFactory<*, *>?
}
