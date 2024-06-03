/*
* Copyright 2024
*/
package com.bitcoin.tipjar.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.bitcoin.tipjar.datasource.local.database.ReceiptImage
import com.bitcoin.tipjar.datasource.local.database.ReceiptImageQueries
import com.bitcoin.tipjar.datasource.local.database.Receipts
import com.bitcoin.tipjar.datasource.local.database.ReceiptsQueries
import com.bitcoin.tipjar.di.annotation.Dispatcher
import com.bitcoin.tipjar.di.annotation.Thread
import com.bitcoin.tipjar.model.Receipt
import com.bitcoin.tipjar.repository.ReceiptRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class ReceiptDataRepository @Inject constructor(
  private val receiptsQueries: ReceiptsQueries,
  private val receiptImageQueries: ReceiptImageQueries,
  @Dispatcher(Thread.IO)
  private val io: CoroutineDispatcher,
) : ReceiptRepository {

  override suspend fun save(amount: Double, tip: Double, path: String?) {
    val id = System.currentTimeMillis()
    val createdAt = System.currentTimeMillis()
    val receipt = Receipts(id, amount, tip, createdAt)
    receiptsQueries.upsert(receipt)
    if (path != null) {
      val image = ReceiptImage(id, path)
      receiptImageQueries.upsert(image)
    }
  }

  override suspend fun load(): Flow<List<Receipt>> {
    return channelFlow {
      receiptsQueries
        .selectAll()
        .asFlow()
        .mapToList(io)
        .map { receipts ->
          receipts.map { receipt ->
            val id = receipt.id
            val amount = receipt.amount
            val tip = receipt.tip
            val createdAt = receipt.createdAt
            val path = receiptImageQueries
              .selectByReceiptId(id)
              .executeAsOneOrNull()
              ?.path
            Receipt(id, amount, tip, createdAt, path)
          }
        }
        .collectLatest { activities ->
          send(activities)
        }
    }
  }

  override suspend fun loadById(id: Long): Flow<Receipt> {
    return channelFlow {
      receiptsQueries
        .selectByReceiptId(id)
        .asFlow()
        .mapToOne(io)
        .map { receipt ->
          val id = receipt.id
          val amount = receipt.amount
          val tip = receipt.tip
          val createdAt = receipt.createdAt
          val path = receiptImageQueries
            .selectByReceiptId(id)
            .executeAsOneOrNull()
            ?.path
          Receipt(id, amount, tip, createdAt, path)
        }
        .collectLatest { receipt ->
          send(receipt)
        }
    }
  }
}
