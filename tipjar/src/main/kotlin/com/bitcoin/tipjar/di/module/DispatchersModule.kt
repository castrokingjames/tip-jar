/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DispatchersModule {

  @Provides
  @Dispatcher(Thread.IO)
  @Singleton
  fun providesIO(): CoroutineDispatcher {
    return Dispatchers.IO
  }

  @Provides
  @Dispatcher(Thread.MAIN)
  @Singleton
  fun providesMain(): CoroutineDispatcher {
    return Dispatchers.Main
  }
}
