package dev.duckbuddyy.carplace.listing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.duckbuddyy.carplace.model.filter.ListingFilter
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem
import dev.duckbuddyy.carplace.util.CarplaceRepository
import dev.duckbuddyy.carplace.util.PAGINATION_SIZE
import dev.duckbuddyy.carplace.util.log

class ListingPaginationSource(
    private val repository: CarplaceRepository,
    private val listingFilter: ListingFilter
) : PagingSource<Int, ListingResponseItem>() {

    override val keyReuseSupported = true

    override fun getRefreshKey(state: PagingState<Int, ListingResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGINATION_SIZE)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGINATION_SIZE)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ListingResponseItem> {
        val offset = params.key ?: 0

        val listingResponse = repository.getListing(
            filter = listingFilter,
            take = params.loadSize,
            skip = offset
        ).getOrElse {
            it.log()
            return LoadResult.Error(it)
        }

        val prevKey = if (offset == 0) {
            null
        } else {
            offset
        }

        val nextKey = if (listingResponse.count() < params.loadSize) {
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