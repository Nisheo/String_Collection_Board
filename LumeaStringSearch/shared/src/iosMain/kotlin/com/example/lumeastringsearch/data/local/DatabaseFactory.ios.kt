/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.Room
import androidx.room3.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * The iOS app sandbox path is only known at runtime, so it has to be queried from
 * NSFileManager. `Documents/` persists across launches and is iCloud-backed.
 */
actual class DatabaseFactory {
    actual fun createBuilder(): RoomDatabase.Builder<StringDatabase> =
        Room.databaseBuilder<StringDatabase>(
            name = "${documentsDirectory()}/${StringDatabase.FILE_NAME}"
        )

    @OptIn(ExperimentalForeignApi::class)
    private fun documentsDirectory(): String {
        val documentsUrl: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentsUrl?.path) { "Could not resolve iOS Documents directory" }
    }
}

