package dev.djakonystar.urlshortener.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://url-shortener-service.p.rapidapi.com")
            .client(OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("x-rapidapi-key", "176e4091b0msh71756890daac2fcp148d02jsn75facf387c45")
                        builder.header("x-rapidapi-host", "url-shortener-service.p.rapidapi.com")
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
                .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
