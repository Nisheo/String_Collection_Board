/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data

import com.example.lumeastringsearch.data.model.AppString
import com.example.lumeastringsearch.data.model.PlatformFilter
import kotlinx.coroutines.flow.Flow

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 30/09/26
 * Original author  : Nishu
 * Description      : Initial version
 */
interface StringRepository {
    fun searchStringsFiltered(query: String, platformFilter: PlatformFilter): Flow<List<AppString>>

    suspend fun getStringByKey(key: String): AppString?
}