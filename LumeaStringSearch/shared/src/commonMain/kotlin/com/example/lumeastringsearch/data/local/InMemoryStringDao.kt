/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import com.example.lumeastringsearch.util.containsIgnoringCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : In-memory [StringDao] backed by the Excel-derived seed data.
 *                    Instances are created and owned by `AppContainer`, not by a global
 *                    singleton, so tests can supply their own data set.
 */

class InMemoryStringDao(
    initialData: List<StringEntity>
) : StringDao {
    private val _strings = MutableStateFlow(initialData)

    override fun searchStringsFiltered(
        query: String,
        includeAndroid: Boolean,
        includeIos: Boolean
    ): Flow<List<StringEntity>> {
        return _strings.map { list ->
            list.filter { item ->
                val matchesQuery = query.isBlank() ||
                        item.key.containsIgnoringCase(query) ||
                        item.value.containsIgnoringCase(query)

                // Union, mirroring the SQL in StringDao.
                val matchesPlatform =
                    (includeAndroid && item.isAndroid) || (includeIos && item.isIos)

                matchesQuery && matchesPlatform
            }.sortedBy { it.rowNumber }
        }
    }

    override suspend fun getStringByKey(key: String): StringEntity? {
        return _strings.value.find { it.key == key }
    }

    override suspend fun count(): Int = _strings.value.size

    override suspend fun insertAll(strings: List<StringEntity>) {
        val current = _strings.value.toMutableList()
        strings.forEach { newEntity ->
            current.removeAll { it.key == newEntity.key }
            current.add(newEntity)
        }
        _strings.value = current
    }

    override suspend fun clearAll() {
        _strings.value = emptyList()
    }
}
