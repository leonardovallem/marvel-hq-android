package com.vallem.marvelhq.shared.presentation.util

import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController

data class FocusClearer(
    private val keyboardController: SoftwareKeyboardController?,
    private val focusManager: FocusManager,
) {
    fun clear(force: Boolean = false) {
        keyboardController?.hide()
        focusManager.clearFocus(force)
    }
}

val LocalFocusClearer = compositionLocalWithComputedDefaultOf {
    FocusClearer(
        keyboardController = LocalSoftwareKeyboardController.currentValue,
        focusManager = LocalFocusManager.currentValue,
    )
}

