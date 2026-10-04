/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.mp.KoinPlatform

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 04/10/26
 * Original author  : Nishu
 * Description      : Single entry point for starting Koin on every platform.
 */
fun initKoin(extra: KoinAppDeclaration? = null) {
    if (KoinPlatform.getKoinOrNull() != null) return

    startKoin {
        extra?.invoke(this)
        modules(appModule, platformModule())
    }
}


