/*
* Copyright 2024
*/
package com.bitcoin.tipjar.usecase

import com.bitcoin.tipjar.model.Receipt
import com.bitcoin.tipjar.repository.ReceiptRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class LoadReceiptsUseCase @Inject constructor(
  private val receiptRepository: ReceiptRepository,
) {

  operator fun invoke(): Flow<List<Receipt>> {
    return channelFlow {
      receiptRepository
        .load()
        .collectLatest { receipts ->
          send(receipts)
        }
    }
  }
}
