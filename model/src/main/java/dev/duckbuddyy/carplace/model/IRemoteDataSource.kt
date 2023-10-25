package dev.duckbuddyy.carplace.model

import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import dev.duckbuddyy.carplace.model.listing.ListingResponse

interface IRemoteDataSource {
    suspend fun getListing(
        categoryId: Int? = null,
        minDate: String? = null,
        maxDate: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        sort: SortType? = null,
        sortDirection: ListSortDirection? = null,
        skip: Int = 0,
        take: Int = 20
    ): Result<ListingResponse>

    suspend fun getDetail(
        id: Int
    ): Result<DetailResponse>
}