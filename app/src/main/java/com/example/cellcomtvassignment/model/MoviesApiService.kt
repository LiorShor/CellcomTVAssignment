package com.example.cellcomtvassignment.model

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApiService {

    @GET("top_rated")
    fun getMoviesAsync(
        @Query("page") page: Int
    ): Deferred<MovieResponse>

    companion object {
        private const val API_KEY = "2c46288716a18fb7aadcc2a801f3fc6b"
        private const val URL = "http://api.themoviedb.org/3/movie/"

        operator fun invoke(): MoviesApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder().client(okHttpClient)
                .baseUrl(URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesApiService::class.java)

        }
    }
}