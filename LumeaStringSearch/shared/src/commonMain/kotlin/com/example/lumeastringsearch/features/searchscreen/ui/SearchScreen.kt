/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.searchscreen.ui


/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 30/09/26
 * Original author  : Nishu
 * Description      : Initial version
 */
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lumeastringsearch.features.searchscreen.SearchViewModel
import lumeastringsearch.shared.generated.resources.Res
import lumeastringsearch.shared.generated.resources.description
import lumeastringsearch.shared.generated.resources.header
import lumeastringsearch.shared.generated.resources.search_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = remember { SearchViewModel() },
    onStringClick: (String) -> Unit = {}
) {
    val query by viewModel.query.collectAsState()
    val selectedPlatformStr by viewModel.selectedPlatform.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Text(
            text = stringResource(Res.string.header),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(Res.string.description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(stringResource(Res.string.search_placeholder))
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    TextButton(onClick = { viewModel.onQueryChanged("") }) {
                        Text("Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilterTag(
            selectedPlatformStr = selectedPlatformStr,
            searchResults = searchResults,
            onPlatformSelected = { viewModel.onPlatformSelected(it) },
            onStringClick = onStringClick,
            query = query
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen()
}