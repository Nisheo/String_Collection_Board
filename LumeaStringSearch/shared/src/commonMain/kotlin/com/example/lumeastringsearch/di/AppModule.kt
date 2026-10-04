/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.di

import com.example.lumeastringsearch.data.StringRepository
import com.example.lumeastringsearch.data.StringRepositoryImpl
import com.example.lumeastringsearch.data.local.StringSeeder
import com.example.lumeastringsearch.features.searchscreen.SearchViewModel
import com.example.lumeastringsearch.features.searchscreen.domain.SearchStringsUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Project          : Lumea
 * Revision History : version 2
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Koin module describing the application object graph.
 *
 *                    Platform-agnostic only. The `StringDao` binding now lives in
 *                    [platformModule] because the Room database needs a platform-specific
 *                    file path (and, on Android, a Context). Keeping it separate also lets
 *                    tests substitute `InMemoryStringDao` without overriding definitions.
 */
val appModule = module {
    singleOf(::StringRepositoryImpl) bind StringRepository::class

    factoryOf(::SearchStringsUseCase)

    factoryOf(::StringSeeder)

    viewModelOf(::SearchViewModel)
}

/**
 * Supplies [com.example.lumeastringsearch.data.local.StringDatabase] and its DAO.
 */
expect fun platformModule(): Module

