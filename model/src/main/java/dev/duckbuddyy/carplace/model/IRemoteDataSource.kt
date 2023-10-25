package dev.duckbuddyy.carplace.model

import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse

interface IRemoteDataSource {
    suspend fun getListing(
        categoryId: Int? = null,
        minDate: String? = null,
        maxDate: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        sort: String? = null,
        sortDirection: String? = null,
        skip: Int,
        take: Int
    ): Result<ListingResponse>

    suspend fun getDetail(
        id: Int
    ): Result<DetailResponse>
}