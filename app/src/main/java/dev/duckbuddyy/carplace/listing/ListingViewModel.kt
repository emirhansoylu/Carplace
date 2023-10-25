package dev.duckbuddyy.carplace.listing

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.util.PAGINATION_SIZE
import dev.duckbuddyy.carplace.model.IRemoteDataSource
import dev.duckbuddyy.carplace.model.filter.ListingFilter
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem
import dev.duckbuddyy.carplace.util.CarplaceRepository
import dev.duckbuddyy.carplace.util.PREFETCH_DISTANCE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repository: CarplaceRepository
) : ViewModel() {
    private var _listingFilterFlow = MutableStateFlow(ListingFilter())

    var listingPagingData: Flow<PagingData<ListingResponseItem>> =
        _listingFilterFlow.flatMapLatest {
            Pager(
                config = PagingConfig(
                    pageSize = PAGINATION_SIZE,
                    initialLoadSize = PAGINATION_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE,
                    enablePlaceholders = false,
                )
            ) {
                ListingPaginationSource(
                    repository = repository,
                    listingFilter = it
                )
            }.flow.flowOn(Dispatchers.IO).cachedIn(viewModelScope)
        }

    private val _navigationFlow = MutableSharedFlow<NavDirections>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    fun onItemClicked(item: ListingResponseItem) = viewModelScope.launch {
        val direction = ListingFragmentDirections.actionListingFragmentToDetailFragment(
            id = item.id
        )
        _navigationFlow.emit(direction)
    }

    fun onFilterClicked() = viewModelScope.launch {
        val direction = ListingFragmentDirections.actionListingFragmentToFilterBottomSheetFragment(
            currentFilter = _listingFilterFlow.value
        )
        _navigationFlow.emit(direction)
    }

    fun onFilterChanged(filter: Bundle) = viewModelScope.launch {
        val currentFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            filter.getParcelable(ListingFilter::class.java.simpleName, ListingFilter::class.java)
        } else {
            filter.getParcelable(ListingFilter::class.java.simpleName) as? ListingFilter
        } ?: ListingFilter()

        _listingFilterFlow.emit(currentFilter)
    }
}