package com.example.lumeastringsearch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.lumeastringsearch.di.initKoin
import lumeastringsearch.shared.generated.resources.Res
import lumeastringsearch.shared.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

fun main() {
    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "LumeaStringSearch",
            icon = painterResource(Res.drawable.logo)
        ) {
            App()
        }
    }
}