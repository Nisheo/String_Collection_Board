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
        WHERE key LIKE '%' || :query || '%'
           OR value LIKE '%' || :query || '%'
        ORDER BY key ASC
        LIMIT 100
        """
    )
    fun searchStrings(query: String): Flow<List<StringEntity>>

    @Query(
        """
        SELECT * FROM app_strings
        WHERE (:isAndroidOnly = 0 OR isAndroid = 1)
          AND (:isIosOnly = 0 OR isIos = 1)
          AND (key LIKE '%' || :query || '%'
               OR value LIKE '%' || :query || '%')
        ORDER BY key ASC
        LIMIT 200
        """
    )
    fun searchStringsFiltered(
        query: String,
        isAndroidOnly: Boolean,
        isIosOnly: Boolean
    ): Flow<List<StringEntity>>

    @Query("SELECT * FROM app_strings WHERE key = :key LIMIT 1")
    suspend fun getStringByKey(key: String): StringEntity?

    @Query("SELECT * FROM app_strings ORDER BY key ASC")
    fun getAllStrings(): Flow<List<StringEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(strings: List<StringEntity>)

    @Query("SELECT COUNT(*) FROM app_strings")
    suspend fun getCount(): Int

    @Query("DELETE FROM app_strings")
    suspend fun clearAll()
}