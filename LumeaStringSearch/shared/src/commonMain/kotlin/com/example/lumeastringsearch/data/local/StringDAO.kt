/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : Initial version
 */
@Dao
interface StringDao {
    @Query(
        """
        SELECT * FROM app_strings
        WHERE ((:includeAndroid = 1 AND isAndroid = 1)
            OR (:includeIos = 1 AND isIos = 1))
          AND (:query = ''
            OR key LIKE '%' || :query || '%'
            OR value LIKE '%' || :query || '%')
        ORDER BY rowNumber ASC
        """
    )
    fun searchStringsFiltered(
        query: String,
        includeAndroid: Boolean,
        includeIos: Boolean
    ): Flow<List<StringEntity>>

    @Query("SELECT * FROM app_strings WHERE key = :key LIMIT 1")
    suspend fun getStringByKey(key: String): StringEntity?

    @Query("SELECT COUNT(*) FROM app_strings")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(strings: List<StringEntity>)

    @Query("DELETE FROM app_strings")
    suspend fun clearAll()
}