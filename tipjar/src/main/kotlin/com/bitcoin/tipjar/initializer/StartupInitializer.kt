/*
* Copyright 2024
*/
package com.bitcoin.tipjar.initializer

import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class StartupInitializer @Inject constructor(
  private val initializers: Set<@JvmSuppressWildcards Initializer>,
  @Dispatcher(Thread.IO) private val dispatcher: CoroutineDispatcher,
) : Initializer {

  override fun invoke() {
    val context = CoroutineScope(dispatcher)
    context.launch {
      initializers.forEach(Initializer::invoke)
    }
  }
}
