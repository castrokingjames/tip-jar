/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {

  @Provides
  @Singleton
  fun providesGson(): Gson {
    return GsonBuilder()
      .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
      .setPrettyPrinting()
      .create()
  }
}
