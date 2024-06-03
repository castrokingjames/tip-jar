/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

  @Provides
  @Singleton
  fun providesContext(application: Application): Context {
    return application
  }
}
