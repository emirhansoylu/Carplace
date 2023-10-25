package dev.duckbuddyy.carplace.listing.filter

import android.text.Editable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import dev.duckbuddyy.carplace.model.filter.ListingFilter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val arguments = FilterBottomSheetFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiStateFlow = MutableStateFlow(FilterBottomSheetState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private val _popBackStackFlow = MutableSharedFlow<ListingFilter>()
    val popBackStackFlow = _popBackStackFlow.asSharedFlow()

    init {
        initializeFilterWithArguments()
    }

    private fun initializeFilterWithArguments() = viewModelScope.launch {
        arguments.currentFilter?.let {
            _uiStateFlow.emit(FilterBottomSheetState(it))
        }
    }

    fun onSortDirectionChanged(sortDirection: ListSortDirection) = viewModelScope.launch {
        val _sortDirection = if (uiStateFlow.value.filter.sortDirection == sortDirection) {
            null
        } else {
            sortDirection
        }

        uiStateFlow.value.filter.copy(
            sortDirection = _sortDirection
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun onSortTypeChanged(sortType: SortType) = viewModelScope.launch {
        val _sortType = if (uiStateFlow.value.filter.sortType == sortType) {
            null
        } else {
            sortType
        }

        uiStateFlow.value.filter.copy(
            sortType = _sortType
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun onMaxDateChanged(maxDate: Editable?) = viewModelScope.launch {
        uiStateFlow.value.filter.copy(
            maxDate = maxDate?.toString()
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun onMinDateChanged(minDate: Editable?) = viewModelScope.launch {
        uiStateFlow.value.filter.copy(
            minDate = minDate?.toString()
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun onMaxYearChanged(maxYear: Editable?) = viewModelScope.launch {
        uiStateFlow.value.filter.copy(
            maxYear = maxYear?.toString()?.toIntOrNull()
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun onMinYearChanged(minYear: Editable?) = viewModelScope.launch {
        uiStateFlow.value.filter.copy(
            minYear = minYear?.toString()?.toIntOrNull()
        ).also { _uiStateFlow.emit(FilterBottomSheetState(it)) }
    }

    fun applyFilters() = viewModelScope.launch {
        _popBackStackFlow.emit(uiStateFlow.value.filter)
    }
}