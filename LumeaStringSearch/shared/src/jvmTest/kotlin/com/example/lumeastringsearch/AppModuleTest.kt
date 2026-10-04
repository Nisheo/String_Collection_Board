package com.example.lumeastringsearch

import com.example.lumeastringsearch.data.StringRepository
import com.example.lumeastringsearch.data.local.InMemoryStringDao
import com.example.lumeastringsearch.data.local.StringDao
import com.example.lumeastringsearch.data.local.StringSeedData
import com.example.lumeastringsearch.di.appModule
import com.example.lumeastringsearch.features.searchscreen.domain.SearchStringsUseCase
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertSame

/**
 * Verifies the Koin graph can actually be constructed and that scoping is correct.
 * Koin resolves at runtime, so a wiring mistake would otherwise only surface on launch.
 *
 * The real `platformModule()` is deliberately NOT used here: it would open a Room
 * database in the developer's home directory. `InMemoryStringDao` stands in for it.
 */
class AppModuleTest {

    private val testDaoModule = module {
        single<StringDao> { InMemoryStringDao(StringSeedData.initialStrings) }
    }

    @Test
    fun `resolves every dependency in the graph`() {
        val koin = koinApplication { modules(appModule, testDaoModule) }.koin

        assertNotNull(koin.get<StringDao>())
        assertNotNull(koin.get<StringRepository>())
        assertNotNull(koin.get<SearchStringsUseCase>())
    }

    @Test
    fun `dao and repository are singletons`() {
        val koin = koinApplication { modules(appModule, testDaoModule) }.koin

        assertSame(koin.get<StringDao>(), koin.get<StringDao>())
        assertSame(koin.get<StringRepository>(), koin.get<StringRepository>())
    }
}

