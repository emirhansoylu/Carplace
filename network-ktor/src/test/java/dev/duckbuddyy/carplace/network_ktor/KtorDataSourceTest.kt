package dev.duckbuddyy.carplace.network_ktor

import dev.duckbuddyy.carplace.model.detail.DetailResponse
import dev.duckbuddyy.carplace.model.listing.ListingResponse
import dev.duckbuddyy.carplace.network_ktor.KtorDataSource.URL.URL_DETAIL
import dev.duckbuddyy.carplace.network_ktor.KtorDataSource.URL.URL_LISTING
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

class KtorDataSourceTest {
    private lateinit var ktorDataSource: KtorDataSource

    @Before
    fun setup() {
        val mockEngine = MockEngine { request ->
            respond(
                content = when (request.url.toString()) {
                    "$URL_LISTING?skip=0&take=10" -> ByteReadChannel(MockData.mockListingJson)
                    "$URL_DETAIL?id=7333920" -> ByteReadChannel(MockData.mockDetailJson)
                    else -> ByteReadChannel(""" {} """)
                },
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        ktorDataSource = KtorDataSource()
        ktorDataSource.client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Test
    fun getListingFromNetwork(): Unit = runBlocking {
        ktorDataSource.getListing(
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
        ktorDataSource.getDetail(
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