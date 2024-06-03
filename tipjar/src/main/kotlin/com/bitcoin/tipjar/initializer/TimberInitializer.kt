/*
* Copyright 2022
*/
package com.bitcoin.tipjar.initializer

import com.bitcoin.tipjar.BuildConfig
import com.bitcoin.tipjar.timber.LogcatTree
import javax.inject.Inject
import timber.log.Timber

class TimberInitializer @Inject constructor() : Initializer {

  override fun invoke() {
    if (BuildConfig.DEBUG) {
      Timber.plant(LogcatTree())
    }
  }
}
