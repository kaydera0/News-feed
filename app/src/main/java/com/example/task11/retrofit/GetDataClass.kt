package com.example.task11.retrofit


data class GetDataClass(
    val status: String, val feed: Object, val items: List<ItemsModel>
)

data class ItemsModel(val title: String, val pubDate: String, val link: String)


