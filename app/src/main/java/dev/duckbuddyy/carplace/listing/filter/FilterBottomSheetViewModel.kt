package dev.duckbuddyy.carplace.listing.filter

import android.os.Bundle
import android.text.Editable
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(FilterBottomSheetState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _setFragmentResultFlow = MutableSharedFlow<Bundle>()
    val setFragmentResultFlow = _setFragmentResultFlow.asSharedFlow()

    fun onSortDirectionChanged(sortDirection: ListSortDirection) = viewModelScope.launch {
        val _sortDirection = if (uiStateFlow.value.sortDirection == sortDirection) {
            null
        } else {
            sortDirection
        }

        uiStateFlow.value.copy(
            sortDirection = _sortDirection
        ).also { _uiStateFlow.emit(it) }
    }

    fun onSortTypeChanged(sortType: SortType) = viewModelScope.launch {
        val _sortType = if (uiStateFlow.value.sortType == sortType) {
            null
        } else {
            sortType
        }

        uiStateFlow.value.copy(
            sortType = _sortType
        ).also { _uiStateFlow.emit(it) }
    }

    fun onMaxDateChanged(maxDate: Editable?) = viewModelScope.launch {
        uiStateFlow.value.copy(
            maxDate = maxDate?.toString()
        ).also { _uiStateFlow.emit(it) }
    }

    fun onMinDateChanged(minDate: Editable?) = viewModelScope.launch {
        uiStateFlow.value.copy(
            minDate = minDate?.toString()
        ).also { _uiStateFlow.emit(it) }
    }

    fun onMaxYearChanged(maxYear: Editable?) = viewModelScope.launch {
        uiStateFlow.value.copy(
            maxYear = maxYear?.toString()?.toIntOrNull()
        ).also { _uiStateFlow.emit(it) }
    }

    fun onMinYearChanged(minYear: Editable?) = viewModelScope.launch {
        uiStateFlow.value.copy(
            minYear = minYear?.toString()?.toIntOrNull()
        ).also { _uiStateFlow.emit(it) }
    }

    fun applyFilters() = viewModelScope.launch {
        _setFragmentResultFlow.emit(uiStateFlow.value.toBundle())
    }
}