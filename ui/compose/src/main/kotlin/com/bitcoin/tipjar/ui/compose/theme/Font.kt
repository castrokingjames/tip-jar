/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.compose.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.bitcoin.tipjar.ui.compose.R

object Font {

  val roboto = FontFamily(
    Font(
      R.font.roboto_regular,
      style = FontStyle.Normal,
      weight = FontWeight.W400,
    ),

    Font(
      R.font.roboto_medium,
      style = FontStyle.Normal,
      weight = FontWeight.Medium,
    ),
  )
}
