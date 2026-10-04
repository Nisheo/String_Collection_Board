/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import java.io.File

/**
 * Desktop has no sandbox, so we pick an explicit dot-directory under the user's home
 * rather than the working directory (which changes depending on how the app is launched).
 */
actual class DatabaseFactory {
    actual fun createBuilder(): RoomDatabase.Builder<StringDatabase> {
        val appDir = File(System.getProperty("user.home"), ".lumea").apply { mkdirs() }
        val dbFile = File(appDir, StringDatabase.FILE_NAME)
        return Room.databaseBuilder<StringDatabase>(name = dbFile.absolutePath)
    }
}

