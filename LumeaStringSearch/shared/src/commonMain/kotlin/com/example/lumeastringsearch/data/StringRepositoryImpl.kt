/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data

import com.example.lumeastringsearch.data.local.StringDao
import com.example.lumeastringsearch.data.local.StringEntity
import com.example.lumeastringsearch.data.model.AppString
import com.example.lumeastringsearch.data.model.PlatformFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 30/09/26
 * Original author  : Nishu
 * Description      : Initial version
 */
class StringRepositoryImpl(
    private val dao: StringDao
) : StringRepository {
    override fun searchStringsFiltered(
        query: String,
        platformFilter: PlatformFilter
    ): Flow<List<AppString>> {
        return dao.searchStringsFiltered(
            query = query,
            includeAndroid = platformFilter.includeAndroid,
            includeIos = platformFilter.includeIos
        ).map { entities ->
            entities.map { entity -> entity.toAppString() }
        }
    }

    override suspend fun getStringByKey(key: String): AppString? {
        return dao.getStringByKey(key)?.toAppString()
    }

    private fun StringEntity.toAppString(): AppString = AppString(
        key = key,
        value = value,
        isAndroid = isAndroid,
        isIos = isIos,
        language = language,
        rowNumber = rowNumber
    )
}