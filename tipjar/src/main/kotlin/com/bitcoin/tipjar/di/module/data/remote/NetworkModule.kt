/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module.data.remote

import dagger.Module

@Module(
  includes = [
    ServiceModule::class,
    InterceptorModule::class,
  ],
)
class NetworkModule
