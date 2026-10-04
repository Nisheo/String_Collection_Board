/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : Initial version
 */

@Entity(
    tableName = "app_strings",
    indices = [
        Index(value = ["key"]),
        Index(value = ["rowNumber"])
    ]
)
data class StringEntity(
    @PrimaryKey
    val key: String,
    val value: String,
    val isAndroid: Boolean = false,
    val isIos: Boolean = true,
    val language: String = "English",
    val rowNumber: Int = 0
)
