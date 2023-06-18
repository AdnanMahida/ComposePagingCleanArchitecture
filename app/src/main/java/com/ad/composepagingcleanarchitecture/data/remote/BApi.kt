package com.ad.composepagingcleanarchitecture.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface BApi {

    @GET("beers")
    suspend fun getBs(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int,
    ): List<BDto>


    companion object {
        const val BASE_URL = "https://api.punkapi.com/v2/"
    }
}