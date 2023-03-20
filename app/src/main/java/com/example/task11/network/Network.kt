package com.example.task11.network

import com.example.task11.network.retrofit.RetrofitApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Network {

    val urlReddit = "http://www.reddit.com/"
    val urlFeedburner = "https://feeds.feedburner.com"

    fun getRetrofitApi(url: String): RetrofitApi {
        val gson = GsonBuilder().setLenient().create()
        val retrofitSimpleCast = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofitSimpleCast.create(RetrofitApi::class.java)
    }
}