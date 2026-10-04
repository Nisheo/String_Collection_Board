package com.example.lumeastringsearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.lumeastringsearch.data.local.StringSeeder
import com.example.lumeastringsearch.navigation.AppNavHost
import org.koin.compose.koinInject

@Composable
fun App() {
    val seeder: StringSeeder = koinInject()

    LaunchedEffect(Unit) {
        seeder.seedIfEmpty()
    }

    MaterialTheme {
        AppNavHost(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        )
    }
}