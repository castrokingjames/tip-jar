/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.bitcoin.tipjar.di.annotation.ViewModelKey
import com.bitcoin.tipjar.feature.history.HistoryScreenViewModel
import com.bitcoin.tipjar.feature.home.HomeScreenViewModel
import com.bitcoin.tipjar.ui.viewmodel.AssistedViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(HomeScreenViewModel::class)
  abstract fun homeScreenViewModelFactory(factory: HomeScreenViewModel.Factory?): AssistedViewModelFactory<*, *>

  @Binds
  @IntoMap
  @ViewModelKey(HistoryScreenViewModel::class)
  abstract fun historyScreenViewModelFactory(factory: HistoryScreenViewModel.Factory?): AssistedViewModelFactory<*, *>
}
