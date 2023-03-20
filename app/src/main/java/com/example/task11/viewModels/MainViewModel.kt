package com.example.task11.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task11.R
import com.example.task11.network.Network
import com.example.task11.network.retrofit.RetrofitApi
import com.example.task11.repositories.RssRepository
import com.example.task11.room.RoomDB
import com.example.task11.room.RoomNews
import com.example.task11.uiElements.NewsUiElement
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val roomDb: RoomDB,
    private val network: Network,
    private val rssRepository: RssRepository
) : ViewModel() {

    val redditIsChecked = MutableLiveData(true)
    val feedIsChecked = MutableLiveData(true)
    val news = MutableLiveData<List<NewsUiElement>>()
    val favNews = MutableLiveData<ArrayList<NewsUiElement>>()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val news = getNewsFromDB()
            favNews.postValue(news)
        }
    }

    fun callRssNews() {
        CoroutineScope(Dispatchers.IO).launch {
            val redditRss = network.getRetrofitApi(network.urlReddit).getRedditRss()
            val FeedburnerRss = network.getRetrofitApi(network.urlFeedburner).getFeedBurnRss()

            if (redditIsChecked.value == true && feedIsChecked.value == true) {
                news.postValue(favNews.value!!+
                    rssRepository.sortFeedburnerRss(FeedburnerRss) + rssRepository.sortRedditRss(redditRss, context))
            }
            if (redditIsChecked.value == true && feedIsChecked.value == false) {
                news.postValue(favNews.value!! + rssRepository.sortRedditRss(redditRss, context))
            }
            if (redditIsChecked.value == false && feedIsChecked.value == true) {
                news.postValue( favNews.value!! + rssRepository.sortFeedburnerRss(FeedburnerRss))
            }
        }
    }

    suspend fun addElementToDB(newsUiElement: NewsUiElement){
        roomDb.roomNewsDao()?.insertNews(
            RoomNews(
                0,
                newsUiElement.image.toString(),
                newsUiElement.date,
                newsUiElement.title,
                newsUiElement.source,
                newsUiElement.link
            )
        )
    }
    suspend fun deleteFromDB(newsUiElement: NewsUiElement){
        roomDb.roomNewsDao()?.deleteByUrl(newsUiElement.link!!)
    }

    suspend fun getNewsFromDB(): ArrayList<NewsUiElement> {
        val list = roomDb.roomNewsDao()?.getNews()
        val arrayList = ArrayList<NewsUiElement>()
        if (list != null) {
            for (i in list) {
                val newsUiElement = NewsUiElement(
                    Uri.parse(i.image), i.date, i.title, i.source, when (i.source) {
                        "Feedburner" -> context.getDrawable(R.drawable.feedburner_background)
                        else -> context.getDrawable(R.drawable.reddit_background)
                    }, context.getDrawable(R.drawable.baseline_bookmark_orange),
                    context.getDrawable(R.drawable.baseline_bookmark_border_24),
                    i.link, true
                )
                arrayList.add(newsUiElement)
            }
        }
        return arrayList
    }
}