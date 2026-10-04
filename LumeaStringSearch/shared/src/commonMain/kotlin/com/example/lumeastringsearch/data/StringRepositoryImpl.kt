/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data

import com.example.lumeastringsearch.data.local.StringDao
import com.example.lumeastringsearch.data.model.AppString
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
    override fun searchStringsFiltered(query: String, platformFilter: String): Flow<List<AppString>> {
        val isAndroidOnly = platformFilter.equals("Android", ignoreCase = true)
        val isIosOnly = platformFilter.equals("iOS", ignoreCase = true)

        return dao.searchStringsFiltered(query, isAndroidOnly, isIosOnly).map { entities ->
            entities.map { entity ->
                AppString(
                    key = entity.key,
                    value = entity.value,
                    isAndroid = entity.isAndroid,
                    isIos = entity.isIos,
                    language = entity.language
                )
            }
        }
    }
}