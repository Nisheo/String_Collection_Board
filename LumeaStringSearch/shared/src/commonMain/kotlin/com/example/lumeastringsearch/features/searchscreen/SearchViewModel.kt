/**
 * Copyright (c) 2026. Philips Electronics India Ltd
 * All rights reserved. Reproduction in whole or in part is prohibited without
 * the written consent of the copyright holder.
 */

package com.example.lumeastringsearch.features.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lumeastringsearch.features.searchscreen.domain.SearchStringsUseCase
import com.example.lumeastringsearch.features.searchscreen.domain.model.StringItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * Project          : Lumea
 * Revision History : version 1
 * Date             : 30/09/26
 * Original author  : Nishu
 * Description      : ViewModel for string search screen.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchStringsUseCase: SearchStringsUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _selectedPlatform = MutableStateFlow("All")
    val selectedPlatform: StateFlow<String> = _selectedPlatform

    val searchResults: StateFlow<List<StringItem>> =
        combine(_query, _selectedPlatform) { q, p ->
        Pair(q, p)
    }.flatMapLatest { (q, p) ->
        searchStringsUseCase(q, p)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun onPlatformSelected(platform: String) {
        _selectedPlatform.value = platform
    }
}
