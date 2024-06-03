/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.bitcoin.tipjar.initializer.Initializer
import com.bitcoin.tipjar.initializer.MavericksInitializer
import com.bitcoin.tipjar.initializer.StartupInitializer
import com.bitcoin.tipjar.initializer.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class InitializerModule {

  @Binds
  @IntoSet
  abstract fun bindsMavericksInitializer(initializer: MavericksInitializer): Initializer

  @Binds
  @IntoSet
  abstract fun bindsTimberInitializer(initializer: TimberInitializer): Initializer

  companion object {

    @Provides
    @Singleton
    fun providesStartupInitializer(initializer: StartupInitializer): Initializer {
      return initializer
    }
  }
}
