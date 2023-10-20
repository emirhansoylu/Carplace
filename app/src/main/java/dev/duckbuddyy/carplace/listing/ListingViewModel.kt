package dev.duckbuddyy.carplace.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem
import dev.duckbuddyy.carplace.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    var listingFlow: Flow<PagingData<ListingResponseItem>> = getCurrentListingFlow()
        private set

    /**
     * Returns the current listing pagination source.
     * It should be triggered when filters updated.
     */
    private fun getCurrentListingFlow() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
        )
    ) { ListingPaginationSource(repository) }
        .flow
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}