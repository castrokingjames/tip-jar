/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.bitcoin.tipjar.di.annotation.ViewModelKey
import com.bitcoin.tipjar.ui.viewmodel.AssistedViewModelFactory
import com.bitcoin.tipjar.ui.viewmodel.SimpleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(SimpleViewModel::class)
  abstract fun simpleViewModelFactory(factory: SimpleViewModel.Factory?): AssistedViewModelFactory<*, *>
}
