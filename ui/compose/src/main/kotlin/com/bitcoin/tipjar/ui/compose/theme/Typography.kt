/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.bitcoin.tipjar.ui.compose.theme.Font.roboto

class TypographyResource {

  val regular: TextStyle = TextStyle(
    fontFamily = roboto,
    fontWeight = FontWeight.W400,
    fontSize = TextUnit(16f, TextUnitType.Sp),
  )

  val medium: TextStyle = TextStyle(
    fontFamily = roboto,
    fontWeight = FontWeight.Medium,
    fontSize = TextUnit(16f, TextUnitType.Sp),
  )

  val regular24: TextStyle = regular.copy(
    fontSize = TextUnit(24f, TextUnitType.Sp),
  )

  val regular42: TextStyle = regular.copy(
    fontSize = TextUnit(42f, TextUnitType.Sp),
  )
}

val Typography: TypographyResource @Composable get() = typographyComposition.current

val typographyComposition = compositionLocalOf { TypographyResource() }
