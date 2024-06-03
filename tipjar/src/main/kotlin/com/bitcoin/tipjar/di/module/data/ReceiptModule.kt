/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module.data

import com.bitcoin.tipjar.data.ReceiptDataRepository
import com.bitcoin.tipjar.repository.ReceiptRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReceiptModule {

  @Provides
  @Singleton
  fun providesReceiptRepository(repository: ReceiptDataRepository): ReceiptRepository {
    return repository
  }
}
