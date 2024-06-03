/*
* Copyright 2024
*/
package com.bitcoin.tipjar.model

data class Receipt(
  val id: Long,
  val amount: Double,
  val tip: Double,
  val createdAt: Long,
  val path: String? = null,
)
