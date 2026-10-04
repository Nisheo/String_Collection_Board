package com.example.lumeastringsearch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lumeastringsearch.di.appModule
import com.example.lumeastringsearch.navigation.AppNavHost
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
@Preview
fun App() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(appModule)
        }
    ) {
        MaterialTheme {
            AppNavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
            )
        }
    }
}