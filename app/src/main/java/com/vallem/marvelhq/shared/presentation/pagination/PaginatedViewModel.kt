package com.vallem.marvelhq.shared.presentation.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class PaginatedViewModel(private val initialPage: Int) : ViewModel() {
    var refreshState by mutableStateOf(PaginationState.Refresh.NotLoading)
        private set
    var appendState by mutableStateOf(PaginationState.Append.NotLoading)
        protected set

    private var job: Job? = null
    private var currentPage = initialPage

    protected abstract suspend fun retrieveData(page: Int): Boolean

    fun loadNextPage() {
        job?.cancel()
        job = viewModelScope.launch {
            if (currentPage == initialPage) {
                refreshState = PaginationState.Refresh.Loading

                refreshState = if (retrieveData(currentPage++)) PaginationState.Refresh.NotLoading else PaginationState.Refresh.Error
            } else {
                appendState = PaginationState.Append.Loading

                appendState = if (retrieveData(++currentPage)) PaginationState.Append.NotLoading else PaginationState.Append.Error
            }
        }
    }

    fun retry() {
        job?.cancel()
        job = viewModelScope.launch {
            appendState = PaginationState.Append.Loading
            appendState = if (retrieveData(currentPage)) PaginationState.Append.NotLoading else PaginationState.Append.Error
        }
    }

    fun refresh() {
        job?.cancel()
        job = viewModelScope.launch {
            refreshState = PaginationState.Refresh.Loading

            currentPage = initialPage
            refreshState = if (retrieveData(initialPage)) PaginationState.Refresh.NotLoading else PaginationState.Refresh.Error
        }
    }
}