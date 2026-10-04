/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import android.content.Context
import androidx.room3.Room
import androidx.room3.RoomDatabase

/**
 * `getDatabasePath` returns the standard `/data/data/<pkg>/databases/` location,
 * which is private to the app and included in Android's backup rules.
 */
actual class DatabaseFactory(private val context: Context) {
    actual fun createBuilder(): RoomDatabase.Builder<StringDatabase> {
        val dbFile = context.getDatabasePath(StringDatabase.FILE_NAME)
        return Room.databaseBuilder<StringDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath
        )
    }
}

