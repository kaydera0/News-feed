package com.example.task11.retrofit

import retrofit2.http.GET

interface RetrofitApi {
    @GET("v1/api.json?rss_url=http%3A%2F%2Fwww.reddit.com%2F.rss")
    suspend fun getRedditRss():GetDataClass
    @GET("v1/api.json?rss_url=https%3A%2F%2Ffeeds.simplecast.com%2F54nAGcIl")
    suspend fun getSimpleCastRss():GetDataClass
}