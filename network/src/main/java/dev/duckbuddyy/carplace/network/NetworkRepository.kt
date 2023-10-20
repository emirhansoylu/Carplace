package dev.duckbuddyy.carplace.network

import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkRepository {
    internal var client: HttpClient = NetworkModule.httpClient

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
    suspend fun getListing(
        categoryId: Int? = null,
        minDate: String? = null,
        maxDate: String? = null,
        minYear: Int? = null,
        maxYear: Int? = null,
        sort: Int? = null,
        sortDirection: Int? = null,
        skip: Int = 0,
        take: Int = 10
    ): Result<ListingResponse> = withContext(Dispatchers.IO) {
        runCatching {
            client.get(URL_LISTING) {
                method = HttpMethod.Get
                url {
                    categoryId?.let { parameters.append("categoryId", it.toString()) }
                    minDate?.let { parameters.append("minDate", it) }
                    maxDate?.let { parameters.append("maxDate", it) }
                    minYear?.let { parameters.append("minYear", it.toString()) }
                    maxYear?.let { parameters.append("maxYear", it.toString()) }
                    sort?.let { parameters.append("sort", it.toString()) }
                    sortDirection?.let { parameters.append("sortDirection", it.toString()) }
                    parameters.append("skip", skip.toString())
                    parameters.append("take", take.toString())
                }
            }.body()
        }
    }

    /**
     * Gets the item detail from network.
     *
     * @param id Identifier of the car detail that that retrieved from network.
     */
    suspend fun getDetail(
        id: Int
    ): Result<DetailResponse> = withContext(Dispatchers.IO) {
        runCatching {
            client.get(URL_DETAIL) {
                method = HttpMethod.Get
                url {
                    parameters.append("id", id.toString())
                }
            }.body()
        }
    }

    internal companion object URL {
        const val URL_LISTING = BuildConfig.BASE_URL + "v1/listing"
        const val URL_DETAIL = BuildConfig.BASE_URL + "v1/detail"
    }
}