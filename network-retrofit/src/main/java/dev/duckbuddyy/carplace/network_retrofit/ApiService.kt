package dev.duckbuddyy.carplace.network_retrofit

import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import dev.duckbuddyy.carplace.network_retrofit.RetrofitRepository.URL.URL_DETAIL
import dev.duckbuddyy.carplace.network_retrofit.RetrofitRepository.URL.URL_LISTING
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(URL_LISTING)
    suspend fun getListing(
        @Query("categoryId") categoryId: String?,
        @Query("minDate") minDate: String?,
        @Query("maxDate") maxDate: String?,
        @Query("minYear") minYear: String?,
        @Query("maxYear") maxYear: String?,
        @Query("sort") sort: String?,
        @Query("sortDirection") sortDirection: String?,
        @Query("skip") skip: String,
        @Query("take") take: String,
    ): ListingResponse

    @GET(URL_DETAIL)
    suspend fun getDetail(
        @Query("id") id: String,
    ): DetailResponse
}