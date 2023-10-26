package dev.duckbuddyy.carplace.network_retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import dev.duckbuddyy.carplace.network_retrofit.RetrofitDataSource.URL.URL_DETAIL
import dev.duckbuddyy.carplace.network_retrofit.RetrofitDataSource.URL.URL_LISTING
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection

class RetrofitDataSourceTest {
    private var mockWebServer = MockWebServer()

    private lateinit var retrofitDataSource: RetrofitDataSource

    @Before
    fun setup() {
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "$URL_LISTING?skip=0&take=10" -> MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(MockData.mockListingJson)

                    "$URL_DETAIL?id=7333920" -> MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(MockData.mockDetailJson)

                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
        mockWebServer.dispatcher = dispatcher
        mockWebServer.start()

        retrofitDataSource = RetrofitDataSource()
        retrofitDataSource.apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getListingFromNetwork(): Unit = runBlocking {
        retrofitDataSource.getListing(
            skip = 0,
            take = 10
        ).onSuccess { networkListing: ListingResponse ->
            assert(networkListing == MockData.mockListingObject) {
                val differences = networkListing.subtract(MockData.mockListingObject)
                "Network listing must be same as mock listing.\nThe lists has ${differences.size} differences.\nThey are: $differences"
            }
        }.onFailure {
            assert(false) {
                "Network request must be succeeded. The error is: ${it.localizedMessage}"
            }
        }
    }

    @Test
    fun getDetailFromNetwork(): Unit = runBlocking {
        retrofitDataSource.getDetail(
            id = 7333920
        ).onSuccess { networkDetail: DetailResponse ->
            assert(networkDetail == MockData.mockDetailObject) {
                "Network car detail must be same as mock car detail."
            }
        }.onFailure {
            assert(false) {
                "Network request must be succeeded. The error is: ${it.localizedMessage}"
            }
        }
    }
}