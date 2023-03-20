package com.example.task11.network.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {
    @GET("r/news/.rss")
    suspend fun getRedditRss(): Response<String>
//    @GET("/techcrunch")
    @GET("/Mashable")
    suspend fun getFeedBurnRss():Response<String>
}