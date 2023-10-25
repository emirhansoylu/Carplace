package dev.duckbuddyy.carplace.util

import dev.duckbuddyy.carplace.model.IRemoteDataSource
import dev.duckbuddyy.carplace.model.filter.ListingFilter

class CarplaceRepository(
    private val dataSource: IRemoteDataSource
) {
    suspend fun getListing(
        filter: ListingFilter,
        skip: Int,
        take: Int
    ) = dataSource.getListing(
        categoryId = null,
        minDate = filter.minDate,
        maxDate = filter.maxDate,
        minYear = filter.minYear?.toIntOrNull(),
        maxYear = filter.maxYear?.toIntOrNull(),
        sort = filter.sortType?.sortType,
        sortDirection = filter.sortDirection?.direction,
        skip = skip,
        take = take
    ).onFailure { it.log() }

    suspend fun getDetail(
        id: Int
    ) = dataSource.getDetail(
        id = id
    ).onFailure { it.log() }
}