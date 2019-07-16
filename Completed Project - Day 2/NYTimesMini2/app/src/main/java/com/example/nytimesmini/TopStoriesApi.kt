package com.example.nytimesmini

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TopStoriesApi {

    @GET("/svc/topstories/v2/{section}.json")
    fun getSection(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): Deferred<Response<Section>>
}

object ApiFactory {
    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(OkHttpClient().newBuilder().build())
        .baseUrl("https://api.nytimes.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val topStoriesApi: TopStoriesApi = retrofit().create(TopStoriesApi::class.java)
}