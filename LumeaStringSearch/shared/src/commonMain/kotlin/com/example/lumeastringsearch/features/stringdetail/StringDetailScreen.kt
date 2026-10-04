/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.stringdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lumeastringsearch.data.StringRepository
import com.example.lumeastringsearch.data.model.AppString
import org.koin.compose.koinInject

@Composable
fun StringDetailScreen(
    stringKey: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var stringDetail by remember { mutableStateOf<AppString?>(null) }
    val repository: StringRepository = koinInject()

    LaunchedEffect(stringKey, repository) {
        stringDetail = repository.getStringByKey(stringKey)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "String details",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "KEY",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringKey,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ENGLISH VALUE",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = stringDetail?.value ?: "Loading...",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "PLATFORMS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                val platformsList = mutableListOf<String>()
                if (stringDetail?.isAndroid == true) platformsList.add("Android")
                if (stringDetail?.isIos == true) platformsList.add("iOS")

                Text(
                    text = if (platformsList.isNotEmpty()) platformsList.joinToString(", ") else "iOS",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                val rowNumber = stringDetail?.rowNumber ?: 0
                if (rowNumber > 0) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "EXCEL ROW",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Row $rowNumber \u2022 LumeaLocalization1oct.xlsx (Shopping Cart)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
