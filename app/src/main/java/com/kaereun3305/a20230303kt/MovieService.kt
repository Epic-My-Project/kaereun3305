package com.kaereun3305.a20230303kt

import retrofit2.Call
import retrofit2.http.GET

interface MovieService {
    @GET("https://a4aa-117-16-195-25.jp.ngrok.io/movie-recommand/")
    fun getMovies(): Call<ServerResponse>
}

data class ServerResponse(
    val data: List<movies>
)