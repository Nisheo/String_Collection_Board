/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes.
 *
 * Every destination is a @Serializable object (no arguments) or data class
 * (with arguments). Navigation Compose builds the route/argument types for you,
 * so there are no string routes to keep in sync.
 */
sealed interface Route {

    @Serializable
    data object Search : Route

    @Serializable
    data class StringDetail(val key: String) : Route
}

