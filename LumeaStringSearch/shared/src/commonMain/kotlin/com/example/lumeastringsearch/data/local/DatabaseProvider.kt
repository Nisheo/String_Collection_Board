/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : In-memory string DAO and database provider initialized with Excel seed data.
 */
object DatabaseProvider {
    val dao: StringDao by lazy { InMemoryStringDao(StringSeedData.initialStrings) }
}

class InMemoryStringDao(
    initialData: List<StringEntity>
) : StringDao {
    private val _strings = MutableStateFlow(initialData)

    override fun searchStrings(query: String): Flow<List<StringEntity>> {
        return _strings.map { list ->
            if (query.isBlank()) list
            else list.filter {
                it.key.contains(query, ignoreCase = true) ||
                it.value.contains(query, ignoreCase = true)
            }
        }
    }

    override fun searchStringsFiltered(
        query: String,
        isAndroidOnly: Boolean,
        isIosOnly: Boolean
    ): Flow<List<StringEntity>> {
        return _strings.map { list ->
            list.filter { item ->
                val matchesQuery = query.isBlank() ||
                        item.key.contains(query, ignoreCase = true) ||
                        item.value.contains(query, ignoreCase = true)

                val matchesPlatform = when {
                    isAndroidOnly -> item.isAndroid
                    isIosOnly -> item.isIos
                    else -> true
                }

                matchesQuery && matchesPlatform
            }
        }
    }

    override suspend fun getStringByKey(key: String): StringEntity? {
        return _strings.value.find { it.key == key }
    }

    override fun getAllStrings(): Flow<List<StringEntity>> {
        return _strings
    }

    override suspend fun insertAll(strings: List<StringEntity>) {
        val current = _strings.value.toMutableList()
        strings.forEach { newEntity ->
            current.removeAll { it.key == newEntity.key }
            current.add(newEntity)
        }
        _strings.value = current
    }

    override suspend fun getCount(): Int {
        return _strings.value.size
    }

    override suspend fun clearAll() {
        _strings.value = emptyList()
    }
}
