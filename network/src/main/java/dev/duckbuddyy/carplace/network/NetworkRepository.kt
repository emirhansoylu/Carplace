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
     */
    suspend fun getCars(
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
     * @param id Identifier of the car detail that that retrieved from network.
     */
    suspend fun getCarDetail(
        id: Int
    ): Result<DetailResponse> = withContext(Dispatchers.IO) {
        runCatching {
            val url = URL_DETAIL
            client.get(url).body()
        }
    }

    private companion object URL {
        const val URL_LISTING = BuildConfig.BASE_URL + "v1/listing"
        const val URL_DETAIL = BuildConfig.BASE_URL + "v1/detail"
    }
}