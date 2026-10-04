/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 02/10/26
 * Original author  : Nishu
 * Description      : Initial version
 */
@Database(entities = [StringEntity::class], version = 1, exportSchema = true)
abstract class StringDatabase : RoomDatabase() {
    abstract fun stringDao(): StringDao
}