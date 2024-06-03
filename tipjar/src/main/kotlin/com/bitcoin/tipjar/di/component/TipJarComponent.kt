/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.component

import android.app.Application
import com.bitcoin.tipjar.TipJarApplication
import com.bitcoin.tipjar.di.module.ApplicationModule
import com.bitcoin.tipjar.di.module.ConfigModule
import com.bitcoin.tipjar.di.module.DispatchersModule
import com.bitcoin.tipjar.di.module.GsonModule
import com.bitcoin.tipjar.di.module.InitializerModule
import com.bitcoin.tipjar.di.module.ViewManagerModule
import com.bitcoin.tipjar.di.module.data.local.DatabaseModule
import com.bitcoin.tipjar.di.module.data.remote.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    ApplicationModule::class,
    DispatchersModule::class,
    InitializerModule::class,
    ConfigModule::class,
    GsonModule::class,
    ViewManagerModule::class,
    NetworkModule::class,
    DatabaseModule::class,
  ],
)
interface TipJarComponent {

  fun inject(application: TipJarApplication)

  @Component.Factory
  interface Factory {
    fun build(
      @BindsInstance application: Application,
    ): TipJarComponent
  }
}
