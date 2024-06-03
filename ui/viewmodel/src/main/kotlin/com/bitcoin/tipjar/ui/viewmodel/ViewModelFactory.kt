/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.viewmodel

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext

class ViewModelFactory<VM : MavericksViewModel<S>, S : MavericksState> constructor(
  private val viewModelClass: Class<VM>,
) : MavericksViewModelFactory<VM, S> {

  override fun create(viewModelContext: ViewModelContext, state: S): VM {
    val application = viewModelContext.activity.application
    if (application !is HasViewModelManager) {
      throw RuntimeException(
        String.format(
          "%s does not implement %s",
          application.javaClass.getCanonicalName(),
          HasViewModelManager::class.java.getCanonicalName(),
        ),
      )
    }
    val viewModelManager = (application as HasViewModelManager).viewModelManager()
    val viewModelFactory = viewModelManager.get(viewModelClass)

    @Suppress("UNCHECKED_CAST")
    val castedViewModelFactory = viewModelFactory as? AssistedViewModelFactory<VM, S>
    val viewModel = castedViewModelFactory?.create(state)
    return viewModel as VM
  }
}

inline fun <reified VM : MavericksViewModel<S>, S : MavericksState> viewModelFactory() = ViewModelFactory(VM::class.java)
