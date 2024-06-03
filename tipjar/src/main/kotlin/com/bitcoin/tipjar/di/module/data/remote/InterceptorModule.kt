/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module.data.remote

import dagger.Module
import dagger.multibindings.Multibinds
import okhttp3.Interceptor

@Module
abstract class InterceptorModule {

  @Multibinds
  abstract fun bindsInterceptors(): Map<Int, @JvmSuppressWildcards Interceptor>
}
