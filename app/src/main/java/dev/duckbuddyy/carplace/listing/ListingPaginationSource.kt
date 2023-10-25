package dev.duckbuddyy.carplace.listing

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.duckbuddyy.carplace.PAGINATION_SIZE
import dev.duckbuddyy.carplace.log
import dev.duckbuddyy.carplace.model.IRemoteDataSource
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import dev.duckbuddyy.carplace.model.listing.ListingResponseItem

class ListingPaginationSource(
    private val repository: IRemoteDataSource,
    private val categoryId: Int? = null,
    private val minDate: String? = null,
    private val maxDate: String? = null,
    private val minYear: Int? = null,
    private val maxYear: Int? = null,
    private val sort: SortType? = null,
    private val sortDirection: ListSortDirection? = null,
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