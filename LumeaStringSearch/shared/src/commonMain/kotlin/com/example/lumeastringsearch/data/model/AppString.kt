/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.model

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : Initial version
 */
data class AppString(
    val key: String,
    val value: String,
    val isAndroid: Boolean = false,
    val isIos: Boolean = true,
    val language: String = "English"
)
