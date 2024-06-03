/*
* Copyright 2024
*/
package com.bitcoin.tipjar.usecase

import com.bitcoin.tipjar.repository.ReceiptRepository
import javax.inject.Inject

class SaveReceiptUseCase @Inject constructor(
  private val receiptRepository: ReceiptRepository,
) {
  suspend operator fun invoke(amount: Double, tip: Double, path: String? = null) {
    receiptRepository.save(amount, tip, path)
  }
}
