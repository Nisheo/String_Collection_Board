/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Supplies a platform-specific on-disk location for the Room database.
 *
 *                    Resolved locations:
 *                      Android : /data/data/<pkg>/databases/lumea_strings.db
 *                      iOS     : <app sandbox>/Documents/lumea_strings.db
 *                      Desktop : ~/.lumea/lumea_strings.db
 */
expect class DatabaseFactory {
    fun createBuilder(): RoomDatabase.Builder<StringDatabase>
}

fun DatabaseFactory.createDatabase(): StringDatabase =
    createBuilder()
        .setDriver(BundledSQLiteDriver())
        .build()


