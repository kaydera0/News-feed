package com.example.task11.viewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task11.R
import com.example.task11.retrofit.RetrofitApi
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
    private val roomDb: RoomDB
) : ViewModel() {

    val redditIsChecked = MutableLiveData(true)
    val feedIsChecked = MutableLiveData(true)
    private val urlReddit = "http://www.reddit.com/"
    private val urlFeedburner = "https://feeds.feedburner.com"
    val news = MutableLiveData<List<NewsUiElement>>()
    val favNews = MutableLiveData<ArrayList<NewsUiElement>>()
    var redditUri =
        Uri.parse("https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png")

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val news = getNewsFromDB()
            favNews.postValue(news)
            Log.d("MY_TAG", news.size.toString() + " size db")
        }
    }

    fun callRssNews() {
        CoroutineScope(Dispatchers.IO).launch {
            val redditRss = getRetrofitApi(urlReddit).getRedditRss()
            val FeedburnerRss = getRetrofitApi(urlFeedburner).getFeedBurnRss()

            if (redditIsChecked.value == true && feedIsChecked.value == true) {
                news.postValue(favNews.value!!+
                    sortRedditRss(redditRss, context) + sortFeedburnerRss(FeedburnerRss))
            }
            if (redditIsChecked.value == true && feedIsChecked.value == false) {
                news.postValue(favNews.value!! + sortRedditRss(redditRss, context))
            }
            if (redditIsChecked.value == false && feedIsChecked.value == true) {
                news.postValue( favNews.value!! + sortFeedburnerRss(FeedburnerRss))
            }

        }
    }

    private fun getRetrofitApi(url: String): RetrofitApi {
        val gson = GsonBuilder().setLenient().create()
        val retrofitSimpleCast = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofitSimpleCast.create(RetrofitApi::class.java)
    }

    private fun sortRedditRss(
        response: Response<String>,
        context: Context
    ): ArrayList<NewsUiElement> {
        val titleArr = response.body().toString().split("title")
        val dateArr = response.body().toString().split("date")
        val linkArr = response.body().toString().split(("link href=\""))
        val finalArr = ArrayList<NewsUiElement>()
        var counter = 1
        for (i in 0..24) {
            if (i % 2 == 1 && i > 3) {
                val date = dateArr[i - 2].replace(">", "").replace("<", "").replace("/", "")
                    .substring(1, 11)
                val title = titleArr[i].replace(">", "").replace("<", "").replace("/", "")
                val link = linkArr[counter].split("\"")[0]
                counter++
                val newsUiElement = NewsUiElement(
                    redditUri,
                    date,
                    title,
                    "Reddit",
                    context.getDrawable(R.drawable.reddit_background),
                    context.getDrawable(R.drawable.baseline_bookmark_orange),
                    context.getDrawable(R.drawable.baseline_bookmark_border_24),
                    link, false
                )
                finalArr.add(newsUiElement)
            }
        }
        return finalArr
    }

    private fun sortFeedburnerRss(response: Response<String>): ArrayList<NewsUiElement> {
        val titleArr = response.body().toString().split("title>")
        val dateArr = response.body().toString().split("pubDate")
        val imageArr = response.body().toString().split("url=\"")
        val linkArr = response.body().toString().split("<link>")
        val finalArr = ArrayList<NewsUiElement>()
        var counter = 2
        for (i in 0..17) {
            if (i % 2 == 1 && i >= 3) {
                val title = titleArr[i].replace(">", "").replace("<", "").replace("/", "")
                val date = dateArr[i - 2].replace(">", "").replace("<", "").replace("/", "")
                    .substring(5, 17)
                val image = imageArr[counter].split("\"")[0]
                val link = linkArr[counter].split("</link>")[0]
                counter++
                val newsUiElement = NewsUiElement(
                    Uri.parse(image),
                    date,
                    title,
                    "Feedburner",
                    context.getDrawable(R.drawable.feedburner_background),
                    context.getDrawable(R.drawable.baseline_bookmark_orange),
                    context.getDrawable(R.drawable.baseline_bookmark_border_24),
                    link, false
                )
                finalArr.add(newsUiElement)
            }
        }
        return finalArr
    }

    suspend fun saveFavoritesDB(newsUiElements: ArrayList<NewsUiElement>) {
        for (i in newsUiElements){
        roomDb.roomNewsDao()?.insertNews(
            RoomNews(
                0,
                i.image.toString(),
                i.date,
                i.title,
                i.source,
                "https://forums.digitalpoint.com/threads/my-website-is-not-published-yet.2116099/"
            )
        )}
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
                    }, context.getDrawable(R.drawable.baseline_bookmark_border_24),
                    context.getDrawable(R.drawable.baseline_bookmark_orange),
                    i.link, true
                )
                arrayList.add(newsUiElement)
            }
        }
        return arrayList
    }
}