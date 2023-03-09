package com.example.task11.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task11.retrofit.GetDataClass
import com.example.task11.retrofit.ItemsModel
import com.example.task11.retrofit.RetrofitApi
import com.example.task11.uiElements.NewsUiElement
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewslineViewModel : ViewModel() {

    private val url = " https://api.rss2json.com/"
    val news = MutableLiveData<List<ItemsModel>>()

    fun retroFinal(){

        val retrofitSimpleCast = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val redditApi = retrofitSimpleCast.create(RetrofitApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val simpleCastRss = redditApi.getSimpleCastRss()
            Log.d("MY_TAG", simpleCastRss.status)
//            Log.d("MY_TAG", result.feed.toString())
            Log.d("MY_TAG", simpleCastRss.items[0].toString())

            val redditRss = redditApi.getRedditRss()
            Log.d("MY_TAG", redditRss.status)
//            Log.d("MY_TAG", result.feed.toString())
            Log.d("MY_TAG", redditRss.items[0].toString())

            news.postValue(simpleCastRss.items.let { list-> simpleCastRss.items + redditRss.items })
        }
    }

    fun convert(list : List<ItemsModel>): ArrayList<NewsUiElement> {
        val finalList = ArrayList<NewsUiElement>()
        for (i in list){
            val newsUiElement = NewsUiElement(null,i.pubDate,i.title,i.link,false)
            finalList.add(newsUiElement)
        }
        return finalList
    }
}