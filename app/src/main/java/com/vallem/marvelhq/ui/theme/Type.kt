package com.vallem.marvelhq.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.vallem.marvelhq.R

private val BebasNeue = FontFamily(Font(R.font.bebas_neue))
private val Comfortaa = FontFamily(Font(R.font.comfortaa_medium))

val Typography
    @Composable get() = MaterialTheme.typography.run {
        copy(
            displayLarge = displayLarge.copy(fontFamily = BebasNeue),
            displayMedium = displayMedium.copy(fontFamily = BebasNeue),
            displaySmall = displaySmall.copy(fontFamily = BebasNeue),
            headlineLarge = headlineLarge.copy(fontFamily = Comfortaa),
            headlineMedium = headlineMedium.copy(fontFamily = Comfortaa),
            headlineSmall = headlineSmall.copy(fontFamily = Comfortaa),
            titleLarge = titleLarge.copy(fontFamily = Comfortaa),
            titleMedium = titleMedium.copy(fontFamily = Comfortaa),
            titleSmall = titleSmall.copy(fontFamily = Comfortaa),
            bodyLarge = bodyLarge.copy(fontFamily = Comfortaa),
            bodyMedium = bodyMedium.copy(fontFamily = Comfortaa),
            bodySmall = bodySmall.copy(fontFamily = Comfortaa),
            labelLarge = labelLarge.copy(fontFamily = Comfortaa),
            labelMedium = labelMedium.copy(fontFamily = Comfortaa),
            labelSmall = labelSmall.copy(fontFamily = Comfortaa),
        )
    }