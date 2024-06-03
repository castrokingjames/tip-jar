/*
* Copyright 2024
*/
package com.bitcoin.tipjar.di.module.data.local

import com.bitcoin.tipjar.datasource.local.database.Database
import com.bitcoin.tipjar.datasource.local.database.ReceiptImageQueries
import com.bitcoin.tipjar.datasource.local.database.ReceiptsQueries
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class QueryModule {

  @Provides
  @Singleton
  fun providesReceiptsQueries(database: Database): ReceiptsQueries {
    return database.receiptsQueries
  }

  @Provides
  @Singleton
  fun providesReceiptImageQueries(database: Database): ReceiptImageQueries {
    return database.receiptImageQueries
  }
}
