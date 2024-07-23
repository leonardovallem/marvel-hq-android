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
        private set

    private var job: Job? = null
    protected var currentPage = initialPage

    protected abstract suspend fun retrieveData(): Boolean

    fun loadNextPage() {
        job?.cancel()
        job = viewModelScope.launch {
            if (currentPage == initialPage) {
                refreshState = PaginationState.Refresh.Loading

                currentPage++
                refreshState = if (retrieveData()) PaginationState.Refresh.NotLoading else PaginationState.Refresh.Error
            } else {
                appendState = PaginationState.Append.Loading

                currentPage++
                appendState = if (retrieveData()) PaginationState.Append.NotLoading else PaginationState.Append.Error
            }
        }
    }

    fun retry() {
        job?.cancel()
        job = viewModelScope.launch {
            appendState = PaginationState.Append.Loading
            appendState = if (retrieveData()) PaginationState.Append.NotLoading else PaginationState.Append.Error
        }
    }

    fun refresh() {
        job?.cancel()
        job = viewModelScope.launch {
            refreshState = PaginationState.Refresh.Loading

            currentPage = initialPage
            refreshState = if (retrieveData()) PaginationState.Refresh.NotLoading else PaginationState.Refresh.Error
        }
    }
}