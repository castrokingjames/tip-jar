/*
* Copyright 2024
*/
package com.bitcoin.tipjar.ui.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.colorResource
import com.bitcoin.tipjar.ui.R

@Composable
fun lightMaterialColorScheme() = lightColorScheme(
  primary = colorResource(R.color.md_theme_light_primary),
  onPrimary = colorResource(R.color.md_theme_light_onPrimary),
  primaryContainer = colorResource(R.color.md_theme_light_primaryContainer),
  onPrimaryContainer = colorResource(R.color.md_theme_light_onPrimaryContainer),
  secondary = colorResource(R.color.md_theme_light_secondary),
  onSecondary = colorResource(R.color.md_theme_light_onSecondary),
  secondaryContainer = colorResource(R.color.md_theme_light_secondaryContainer),
  onSecondaryContainer = colorResource(R.color.md_theme_light_onSecondaryContainer),
  tertiary = colorResource(R.color.md_theme_light_tertiary),
  onTertiary = colorResource(R.color.md_theme_light_onTertiary),
  tertiaryContainer = colorResource(R.color.md_theme_light_tertiaryContainer),
  onTertiaryContainer = colorResource(R.color.md_theme_light_onTertiaryContainer),
  error = colorResource(R.color.md_theme_light_error),
  onError = colorResource(R.color.md_theme_light_onError),
  errorContainer = colorResource(R.color.md_theme_light_errorContainer),
  onErrorContainer = colorResource(R.color.md_theme_light_onErrorContainer),
  background = colorResource(R.color.md_theme_light_background),
  onBackground = colorResource(R.color.md_theme_light_onBackground),
  surface = colorResource(R.color.md_theme_light_surface),
  onSurface = colorResource(R.color.md_theme_light_onSurface),
  surfaceVariant = colorResource(R.color.md_theme_light_surfaceVariant),
  onSurfaceVariant = colorResource(R.color.md_theme_light_onSurfaceVariant),
  inverseSurface = colorResource(R.color.md_theme_light_inverseSurface),
  inverseOnSurface = colorResource(R.color.md_theme_light_inverseOnSurface),
  outline = colorResource(R.color.md_theme_light_outline),
  outlineVariant = colorResource(R.color.md_theme_light_outlineVariant),
  inversePrimary = colorResource(R.color.md_theme_light_inversePrimary),
  surfaceTint = colorResource(R.color.md_theme_light_surfaceTint),
)

@Composable
fun darkMaterialColorScheme() = darkColorScheme(
  primary = colorResource(R.color.md_theme_dark_primary),
  onPrimary = colorResource(R.color.md_theme_dark_onPrimary),
  primaryContainer = colorResource(R.color.md_theme_dark_primaryContainer),
  onPrimaryContainer = colorResource(R.color.md_theme_dark_onPrimaryContainer),
  secondary = colorResource(R.color.md_theme_dark_secondary),
  onSecondary = colorResource(R.color.md_theme_dark_onSecondary),
  secondaryContainer = colorResource(R.color.md_theme_dark_secondaryContainer),
  onSecondaryContainer = colorResource(R.color.md_theme_dark_onSecondaryContainer),
  tertiary = colorResource(R.color.md_theme_dark_tertiary),
  onTertiary = colorResource(R.color.md_theme_dark_onTertiary),
  tertiaryContainer = colorResource(R.color.md_theme_dark_tertiaryContainer),
  onTertiaryContainer = colorResource(R.color.md_theme_dark_onTertiaryContainer),
  error = colorResource(R.color.md_theme_dark_error),
  onError = colorResource(R.color.md_theme_dark_onError),
  errorContainer = colorResource(R.color.md_theme_dark_errorContainer),
  onErrorContainer = colorResource(R.color.md_theme_dark_onErrorContainer),
  background = colorResource(R.color.md_theme_dark_background),
  onBackground = colorResource(R.color.md_theme_dark_onBackground),
  surface = colorResource(R.color.md_theme_dark_surface),
  onSurface = colorResource(R.color.md_theme_dark_onSurface),
  surfaceVariant = colorResource(R.color.md_theme_dark_surfaceVariant),
  onSurfaceVariant = colorResource(R.color.md_theme_dark_onSurfaceVariant),
  inverseSurface = colorResource(R.color.md_theme_dark_inverseSurface),
  inverseOnSurface = colorResource(R.color.md_theme_dark_inverseOnSurface),
  outline = colorResource(R.color.md_theme_dark_outline),
  outlineVariant = colorResource(R.color.md_theme_dark_outlineVariant),
  inversePrimary = colorResource(R.color.md_theme_dark_inversePrimary),
  surfaceTint = colorResource(R.color.md_theme_dark_surfaceTint),
)

@Composable
fun TipJarTheme(
  isSystemInDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colors = if (isSystemInDarkTheme) {
    darkMaterialColorScheme()
  } else {
    lightMaterialColorScheme()
  }

  CompositionLocalProvider(
    typographyComposition provides Typography,
  ) {
    MaterialTheme(
      colorScheme = colors,
      content = content,
    )
  }
}
