/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.searchscreen.domain.model

import lumeastringsearch.shared.generated.resources.Res
import lumeastringsearch.shared.generated.resources.android_platform
import lumeastringsearch.shared.generated.resources.iOS_platform
import org.jetbrains.compose.resources.StringResource

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : Initial version
 */
data class StringItem(
    val key: String,
    val englishText: String,
    val screen: String,
    val platform: StringResource
)

val sampleStrings = listOf(
    StringItem(
        key = "treatment_start_title",
        englishText = "Start your treatment",
        screen = "Treatment",
        platform =  Res.string.android_platform
    ),
    StringItem(
        key = "treatment_history_title",
        englishText = "Your treatment history",
        screen = "Treatment History",
        platform =  Res.string.iOS_platform
    ),
    StringItem(
        key = "treatment_report_title",
        englishText = "View treatment report",
        screen = "Treatment Report",
        platform =  Res.string.android_platform
    ),
    StringItem(
        key = "settings_title",
        englishText = "Settings",
        screen = "Settings",
        platform =  Res.string.iOS_platform
    ),
    StringItem(
        key = "skin_test_instruction",
        englishText = "Test your skin's reaction",
        screen = "Skin Test",
        platform =  Res.string.android_platform
    ),
    StringItem(
        key = "treatment_save_button",
        englishText = "Save treatment",
        screen = "Treatment",
        platform =  Res.string.iOS_platform
    )
)