package com.vallem.marvelhq.shared.presentation.extension

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavDestination

inline fun <reified T> EnterTransition.ifTargetIs(target: NavDestination) = takeIf {
    target.route?.startsWith(T::class.qualifiedName ?: return@takeIf false) == true
}

inline fun <reified T> ExitTransition.ifTargetIs(target: NavDestination) = takeIf {
    target.route?.startsWith(T::class.qualifiedName ?: return@takeIf false) == true
}