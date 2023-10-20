package dev.duckbuddyy.carplace.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal object NetworkModule {
    val httpClient by lazy {
        HttpClient(CIO) {
            engine {
                maxConnectionsCount = 1000
                endpoint {
                    keepAliveTime = 5000
                    connectTimeout = 5000
                    connectAttempts = 5
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = false
                    ignoreUnknownKeys = true
                })
            }
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    level = LogLevel.ALL
                }
            }
        }
    }
}