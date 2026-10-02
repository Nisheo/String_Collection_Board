/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lumeastringsearch.features.searchscreen.ui.SearchScreen
import com.example.lumeastringsearch.features.stringdetail.StringDetailScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Search,
        modifier = modifier
    ) {
        composable<Route.Search> {
            SearchScreen(
                onStringClick = { key ->
                    navController.navigate(Route.StringDetail(key))
                }
            )
        }

        composable<Route.StringDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<Route.StringDetail>()
            StringDetailScreen(
                stringKey = args.key,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

