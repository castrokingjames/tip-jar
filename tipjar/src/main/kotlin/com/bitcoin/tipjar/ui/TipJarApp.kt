/*
* Copyright 2024
*/
@file:OptIn(ExperimentalMaterial3Api::class)

package com.bitcoin.tipjar.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bitcoin.tipjar.ui.compose.component.Background
import com.bitcoin.tipjar.ui.screen.SampleScreen

private const val NAVIGATION_ANIM_DURATION = 300
private const val FADE_IN_ANIM_DURATION = 400

private fun enterTransition() = slideInHorizontally(
  initialOffsetX = { NAVIGATION_ANIM_DURATION },
  animationSpec = tween(
    durationMillis = NAVIGATION_ANIM_DURATION,
    easing = FastOutSlowInEasing,
  ),
) + fadeIn(animationSpec = tween(NAVIGATION_ANIM_DURATION))

private fun exitTransition() = slideOutHorizontally(
  targetOffsetX = { -NAVIGATION_ANIM_DURATION },
  animationSpec = tween(
    durationMillis = NAVIGATION_ANIM_DURATION,
    easing = FastOutSlowInEasing,
  ),
) + fadeOut(animationSpec = tween(NAVIGATION_ANIM_DURATION))

private fun popEnterTransition() = slideInHorizontally(
  initialOffsetX = { -NAVIGATION_ANIM_DURATION },
  animationSpec = tween(
    durationMillis = NAVIGATION_ANIM_DURATION,
    easing = FastOutSlowInEasing,
  ),
) + fadeIn(animationSpec = tween(NAVIGATION_ANIM_DURATION))

private fun popExitTransition() = slideOutHorizontally(
  targetOffsetX = { NAVIGATION_ANIM_DURATION },
  animationSpec = tween(
    durationMillis = NAVIGATION_ANIM_DURATION,
    easing = FastOutSlowInEasing,
  ),
) + fadeOut(animationSpec = tween(NAVIGATION_ANIM_DURATION))

@Composable
fun TipJarApp() {
  val navController = rememberNavController()
  Background {
    NavHost(
      navController = navController,
      startDestination = Screen.SimpleScreen.route,
    ) {
      /** Simple Screen */
      composable(
        route = Screen.SimpleScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(FADE_IN_ANIM_DURATION)) },
        exitTransition = {
          if (initialState.destination.route == Screen.DetailScreen.route) {
            exitTransition()
          } else {
            fadeOut(animationSpec = tween(FADE_IN_ANIM_DURATION))
          }
        },
        popEnterTransition = {
          if (targetState.destination.route == Screen.DetailScreen.route) {
            popEnterTransition()
          } else {
            fadeIn(animationSpec = tween(FADE_IN_ANIM_DURATION))
          }
        },
        popExitTransition = { fadeOut(animationSpec = tween(FADE_IN_ANIM_DURATION)) },
      ) {
        SampleScreen()
      }
    }
  }
}
