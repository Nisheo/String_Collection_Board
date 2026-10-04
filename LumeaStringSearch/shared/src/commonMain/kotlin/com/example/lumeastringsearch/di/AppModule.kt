/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.di

import com.example.lumeastringsearch.data.StringRepository
import com.example.lumeastringsearch.data.StringRepositoryImpl
import com.example.lumeastringsearch.data.local.InMemoryStringDao
import com.example.lumeastringsearch.data.local.StringDao
import com.example.lumeastringsearch.data.local.StringSeedData
import com.example.lumeastringsearch.features.searchscreen.SearchViewModel
import com.example.lumeastringsearch.features.searchscreen.domain.SearchStringsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Koin module describing the application object graph.
 *                    Shared by every platform (Android, iOS, desktop).
 */
val appModule = module {
    single<StringDao> { InMemoryStringDao(StringSeedData.initialStrings) }

    singleOf(::StringRepositoryImpl) bind StringRepository::class

    factoryOf(::SearchStringsUseCase)

    viewModelOf(::SearchViewModel)
}

