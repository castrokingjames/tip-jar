/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val source: Thread)

enum class Thread {
  IO,
  MAIN,
}
