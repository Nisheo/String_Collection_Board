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
    val isAndroid: Boolean = false,
    val isIos: Boolean = true,
    val platform: StringResource = if (isAndroid) Res.string.android_platform else Res.string.iOS_platform
)