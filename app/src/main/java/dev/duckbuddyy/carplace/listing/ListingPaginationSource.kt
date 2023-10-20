package dev.duckbuddyy.carplace.listing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem
import dev.duckbuddyy.carplace.network.NetworkRepository

class ListingPaginationSource(
    private val repository: NetworkRepository,
    private val categoryId: Int? = null,
    private val minDate: String? = null,
    private val maxDate: String? = null,
    private val minYear: Int? = null,
    private val maxYear: Int? = null,
    private val sort: Int? = null,
    private val sortDirection: Int? = null,
    private val pageSize: Int = 10
) : PagingSource<Int, ListingResponseItem>() {

    override fun getRefreshKey(state: PagingState<Int, ListingResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(pageSize)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ListingResponseItem> {
        val offset = params.key ?: 0

        val listingResponse = repository.getListing(
            categoryId = categoryId,
            minDate = minDate,
            maxDate = maxDate,
            minYear = minYear,
            maxYear = maxYear,
            sort = sort,
            sortDirection = sortDirection,
            take = params.loadSize,
            skip = offset
        ).getOrElse {
            return LoadResult.Error(it)
        }

        val prevKey = if (offset == 0) {
            null
        } else {
            offset
        }

        val nextKey = if (listingResponse.isEmpty()) {
            null
        } else {
            offset + params.loadSize
        }

        return LoadResult.Page(
            data = listingResponse,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }
}