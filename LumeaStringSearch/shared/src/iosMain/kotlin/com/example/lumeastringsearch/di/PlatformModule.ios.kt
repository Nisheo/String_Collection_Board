/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.di

import com.example.lumeastringsearch.data.local.DatabaseFactory
import com.example.lumeastringsearch.data.local.StringDao
import com.example.lumeastringsearch.data.local.StringDatabase
import com.example.lumeastringsearch.data.local.createDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<StringDatabase> { DatabaseFactory().createDatabase() }
    single<StringDao> { get<StringDatabase>().stringDao() }
}

