package com.example.lumeastringsearch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform