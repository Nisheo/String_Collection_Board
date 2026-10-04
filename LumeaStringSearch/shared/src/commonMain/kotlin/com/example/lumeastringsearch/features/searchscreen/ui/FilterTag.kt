/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.searchscreen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lumeastringsearch.features.searchscreen.domain.model.StringItem
import com.example.lumeastringsearch.util.equalsIgnoringCase
import lumeastringsearch.shared.generated.resources.Res
import lumeastringsearch.shared.generated.resources.all_platform
import lumeastringsearch.shared.generated.resources.android_platform
import lumeastringsearch.shared.generated.resources.collection
import lumeastringsearch.shared.generated.resources.iOS_platform
import lumeastringsearch.shared.generated.resources.no_results_found
import lumeastringsearch.shared.generated.resources.platform
import lumeastringsearch.shared.generated.resources.results
import lumeastringsearch.shared.generated.resources.try_another_search
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColumnScope.FilterTag(
    selectedPlatformStr: String,
    searchResults: List<StringItem>,
    onPlatformSelected: (String) -> Unit,
    onStringClick: (String) -> Unit,
    query: String
) {
    val platforms = listOf(
        Res.string.all_platform,
        Res.string.android_platform,
        Res.string.iOS_platform
    )

    Text(
        text = stringResource(Res.string.platform),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        platforms.forEach { platformRes ->
            val platformLabel = stringResource(platformRes)
            val isSelected = when {
                selectedPlatformStr.equalsIgnoringCase("Android") && platformRes == Res.string.android_platform -> true
                selectedPlatformStr.equalsIgnoringCase("iOS") && platformRes == Res.string.iOS_platform -> true
                selectedPlatformStr.equalsIgnoringCase("All") && platformRes == Res.string.all_platform -> true
                else -> false
            }

            FilterChip(
                selected = isSelected,
                onClick = {
                    val filterValue = when (platformRes) {
                        Res.string.android_platform -> "Android"
                        Res.string.iOS_platform -> "iOS"
                        else -> "All"
                    }
                    onPlatformSelected(filterValue)
                },
                label = { Text(platformLabel) }
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(if (query.isBlank()) Res.string.collection else Res.string.results),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "${searchResults.size} strings",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (searchResults.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.no_results_found),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(Res.string.try_another_search),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = searchResults, key = { it.key }) { item ->
                StringCollectionCard(
                    item = item,
                    query = query,
                    onClick = { onStringClick(item.key) }
                )
            }
        }
    }
}