package dev.duckbuddyy.carplace.listing.filter

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor() : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(FilterBottomSheetState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun onListDirectionChanged(sortDirection: ListSortDirection) = viewModelScope.launch {
        uiStateFlow.value.copy(
            sortDirection = sortDirection
        ).also { _uiStateFlow.emit(it) }
    }

    fun onSortTypeChanged(sortType: SortType) = viewModelScope.launch {
        uiStateFlow.value.copy(
            sortType = sortType
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
}