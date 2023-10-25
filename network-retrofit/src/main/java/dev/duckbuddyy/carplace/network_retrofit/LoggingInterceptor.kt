package dev.duckbuddyy.carplace.network_retrofit

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        println("Request Url: ${request.url}")

        val response: Response = chain.proceed(request)

        val responseBody: ResponseBody = response.body!!
        val bodyString = responseBody.string()

        val contentType: MediaType? = responseBody.contentType()
        val newResponseBody = bodyString.toResponseBody(contentType)
        return response.newBuilder().body(newResponseBody).build()
    }
}