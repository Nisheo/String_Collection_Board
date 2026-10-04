/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.model

import com.example.lumeastringsearch.util.equalsIgnoringCase

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Which platforms a search should include.
 */
enum class PlatformFilter(val includeAndroid: Boolean, val includeIos: Boolean) {
    ALL(includeAndroid = true, includeIos = true),
    ANDROID(includeAndroid = true, includeIos = false),
    IOS(includeAndroid = false, includeIos = true);

    companion object {
        fun from(raw: String): PlatformFilter = when {
            raw.equalsIgnoringCase("Android") -> ANDROID
            raw.equalsIgnoringCase("iOS") -> IOS
            else -> ALL
        }
    }
}

