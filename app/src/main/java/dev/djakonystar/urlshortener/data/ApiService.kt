package dev.djakonystar.urlshortener.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/shorten")
    suspend fun sendUrl(
        @Body requestUrl: RequestUrl
    ): Response<ResponseUrl>
}
