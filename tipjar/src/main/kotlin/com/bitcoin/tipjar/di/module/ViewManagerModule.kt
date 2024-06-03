/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.airbnb.mvrx.MavericksViewModel
import com.bitcoin.tipjar.ui.viewmodel.AssistedViewModelFactory
import com.bitcoin.tipjar.ui.viewmodel.SimpleViewModelManager
import com.bitcoin.tipjar.ui.viewmodel.ViewModelManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
  includes = [
    ViewModelModule::class,
  ],
)
class ViewManagerModule {

  @Provides
  @Singleton
  fun providesViewModelManager(map: Map<Class<out MavericksViewModel<*>>, @JvmSuppressWildcards AssistedViewModelFactory<*, *>>): ViewModelManager {
    return SimpleViewModelManager(map)
  }
}
