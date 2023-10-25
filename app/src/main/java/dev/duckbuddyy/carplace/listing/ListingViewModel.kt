package dev.duckbuddyy.carplace.listing

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.listing.filter.FilterBottomSheetState
import dev.duckbuddyy.carplace.model.IRemoteDataSource
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repository: IRemoteDataSource
) : ViewModel() {
    var listingFlow: Flow<PagingData<ListingResponseItem>> = getCurrentListingFlow()
        private set

    private var filterState: FilterBottomSheetState? = null

    private val _navigationFlow = MutableSharedFlow<NavDirections>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    /**
     * Returns the current listing pagination source.
     * [listingFlow] should be updated when filters updated.
     */
    fun getCurrentListingFlow() = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
        )
    ) {
        ListingPaginationSource(
            repository = repository,
            minDate = filterState?.minDate,
            maxDate = filterState?.maxDate,
            minYear = filterState?.minYear,
            maxYear = filterState?.maxYear,
            sort = filterState?.sortType,
            sortDirection = filterState?.sortDirection
        )
    }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    fun onItemClicked(item: ListingResponseItem) = viewModelScope.launch {
        val direction = ListingFragmentDirections.actionListingFragmentToDetailFragment(
            id = item.id
        )

        _navigationFlow.emit(direction)
    }

    fun onFilterClicked() = viewModelScope.launch {
        val direction = ListingFragmentDirections.actionListingFragmentToFilterBottomSheetFragment()
        _navigationFlow.emit(direction)
    }

    fun onFilterChanged(filter: Bundle) {
        filterState = FilterBottomSheetState.fromBundle(filter)
        listingFlow = getCurrentListingFlow()
    }
}