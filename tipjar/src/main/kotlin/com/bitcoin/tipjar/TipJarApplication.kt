/*
* Copyright 2024
*/
package com.bitcoin.tipjar

import android.app.Application
import com.bitcoin.tipjar.di.component.DaggerTipJarComponent
import com.bitcoin.tipjar.di.component.TipJarComponent
import com.bitcoin.tipjar.initializer.Initializer
import com.bitcoin.tipjar.ui.viewmodel.HasViewModelManager
import com.bitcoin.tipjar.ui.viewmodel.ViewModelManager
import javax.inject.Inject

class TipJarApplication : Application(), HasViewModelManager {

  private lateinit var component: TipJarComponent

  @Inject
  lateinit var initializer: Initializer

  @Inject
  lateinit var viewModelManager: ViewModelManager

  override fun onCreate() {
    super.onCreate()
    component = DaggerTipJarComponent
      .factory()
      .build(this)
    component.inject(this)

    initializer()
  }

  override fun viewModelManager(): ViewModelManager {
    return viewModelManager
  }
}
