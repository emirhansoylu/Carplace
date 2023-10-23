package dev.duckbuddyy.carplace.network_retrofit

import dev.duckbuddyy.carplace.model.INetworkRepository
import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.enums.ListSortDirection
import dev.duckbuddyy.carplace.model.enums.SortType
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitRepository : INetworkRepository {
    internal var apiService: ApiService = NetworkModule.apiService

    /**
     * Gets the car list from network.
     *
     * @param categoryId Identifier of the category.
     * @param minDate Minimum date of the advertisement.
     * @param maxDate Maximum date of the advertisement.
     * @param minYear Minimum model year of the filtered car.
     * @param maxYear Maximum model year of the filtered car.
     * @param sort Type of the sorting. Price = 0, Date = 1, Year = 2.
     * @param sortDirection Sort direction of the list. Ascending=0, Descending=1.
     * @param skip Skips the size of element.
     * @param take Takes the size of element.
     */
    override suspend fun getListing(
        categoryId: Int?,
        minDate: String?,
        maxDate: String?,
        minYear: Int?,
        maxYear: Int?,
        sort: SortType?,
        sortDirection: ListSortDirection?,
        skip: Int,
        take: Int
    ): Result<ListingResponse> = withContext(Dispatchers.IO) {
        runCatching {
            apiService.getListing(
                categoryId = categoryId?.toString(),
                minDate = minDate,
                maxDate = maxDate,
                minYear = minYear?.toString(),
                maxYear = maxYear?.toString(),
                sort = sort?.sortType,
                sortDirection = sortDirection?.direction,
                skip = skip.toString(),
                take = take.toString()
            )
        }
    }

    /**
     * Gets the item detail from network.
     *
     * @param id Identifier of the car detail that that retrieved from network.
     */
    override suspend fun getDetail(
        id: Int
    ): Result<DetailResponse> = withContext(Dispatchers.IO) {
        runCatching {
            apiService.getDetail(
                id = id.toString()
            )
        }
    }

    internal companion object URL {
        const val URL_LISTING = BuildConfig.BASE_URL + "v1/listing"
        const val URL_DETAIL = BuildConfig.BASE_URL + "v1/detail"
    }
}