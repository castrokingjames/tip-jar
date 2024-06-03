/*
* Copyright 2024
*/
package com.bitcoin.tipjar.repository

import com.bitcoin.tipjar.model.Receipt
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {

  suspend fun save(amount: Double, tip: Double, path: String? = null)

  suspend fun load(): Flow<List<Receipt>>

  suspend fun loadById(id: Long): Flow<Receipt>
}
