package com.example.lumeastringsearch

import androidx.compose.ui.window.ComposeUIViewController
import com.example.lumeastringsearch.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}
