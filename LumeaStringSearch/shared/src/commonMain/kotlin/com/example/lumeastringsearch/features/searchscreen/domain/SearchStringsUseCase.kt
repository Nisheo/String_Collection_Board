/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.searchscreen.domain

import com.example.lumeastringsearch.data.StringRepository
import com.example.lumeastringsearch.features.searchscreen.domain.model.StringItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 30/09/26
 * Original author  : Nishu
 * Description      : UseCase for searching strings from repository.
 */
class SearchStringsUseCase(
    private val repository: StringRepository
) {
    operator fun invoke(query: String, platformFilter: String): Flow<List<StringItem>> {
        return repository.searchStringsFiltered(query, platformFilter).map { list ->
            list.map { appString ->
                StringItem(
                    key = appString.key,
                    englishText = appString.value,
                    isAndroid = appString.isAndroid,
                    isIos = appString.isIos
                )
            }
        }
    }
}
