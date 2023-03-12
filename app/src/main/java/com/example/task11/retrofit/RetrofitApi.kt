package com.example.task11.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {
    @GET("r/news/.rss")
    suspend fun getRedditRss(): Response<String>
    @GET("/techcrunch")
    suspend fun getFeedBurnRss():Response<String>
}