/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Copies the Excel-derived seed data into the database on first launch.
 */
class StringSeeder(
    private val dao: StringDao
) {
    suspend fun seedIfEmpty(): Int {
        if (dao.count() > 0) return 0
        // @Insert with a List runs inside a single transaction.
        dao.insertAll(StringSeedData.initialStrings)
        return StringSeedData.initialStrings.size
    }
}

