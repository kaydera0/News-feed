package com.example.task11.viewModels

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task11.R
import com.example.task11.retrofit.RetrofitApi
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
class NewslineViewModel @Inject constructor(@ApplicationContext val context:Context): ViewModel() {

    private val urlReddit = "http://www.reddit.com/"
    private val urlFeedburner = "https://feeds.feedburner.com"
    val news = MutableLiveData<List<NewsUiElement>>()
    var redditUri = Uri.parse("https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png")

    fun retroFinal() {

        CoroutineScope(Dispatchers.IO).launch {


            val redditRss = getRetrofitApi(urlReddit).getRedditRss()
            val FeedburnerRss = getRetrofitApi(urlFeedburner).getFeedBurnRss()

//            val s = sortRedditRss(redditRss) + sortFeedburnerRss(FeedburnerRss)
//            news.postValue(sortRedditRss(redditRss))
            news.postValue(sortRedditRss(redditRss,context) + sortFeedburnerRss(FeedburnerRss))
//            news.postValue(sortFeedburnerRss(FeedburnerRss))

//            news.postValue(simpleCastRss.items.let { list-> simpleCastRss.items + redditRss.items })
        }
    }

    private fun getRetrofitApi(url:String): RetrofitApi {
        val gson = GsonBuilder().setLenient().create()
        val retrofitSimpleCast = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofitSimpleCast.create(RetrofitApi::class.java)
    }
    private fun sortRedditRss(response: Response<String>,context:Context):ArrayList<NewsUiElement>{
        val titleArr = response.body().toString().split("title")
        val dateArr = response.body().toString().split("date")
        val finalArr = ArrayList<NewsUiElement>()

        for (i in 0..titleArr.size - 1) {
            if (i % 2 == 1 && i > 3) {
                val date = dateArr[i-2].replace(">", "").replace("<", "").replace("/", "").substring(1,11)
                val title = titleArr[i].replace(">", "").replace("<", "").replace("/", "")
                val newsUiElement = NewsUiElement(redditUri, date, title, "Reddit", context.getDrawable(R.drawable.reddit_background),context.getDrawable(R.drawable.baseline_bookmark_orange),context.getDrawable(R.drawable.baseline_bookmark_border_24))

                finalArr.add(newsUiElement)
            }
        }
        return finalArr
    }
    private fun sortFeedburnerRss(response: Response<String>):ArrayList<NewsUiElement>{
        val titleArr = response.body().toString().split("title>")
        val dateArr = response.body().toString().split("pubDate")
        val imageArr = response.body().toString().split("url=\"")
//        Log.d("MY_TAG","image ARR = " + imageArr.toString())
        val finalArr = ArrayList<NewsUiElement>()
        var counter = 0
        for (i in 0..titleArr.size - 1) {

            if (i % 2 == 1 && i > 3) {
                val title = titleArr[i].replace(">", "").replace("<", "").replace("/", "")
                val date = dateArr[i-2].replace(">", "").replace("<", "").replace("/", "").substring(5,17)
                val image = imageArr[counter].split("\"")[0]
                counter++
                val newsUiElement = NewsUiElement(Uri.parse(image), date, title, "Feedburner",context.getDrawable(R.drawable.feedburner_background), context.getDrawable(R.drawable.baseline_bookmark_orange),context.getDrawable(R.drawable.baseline_bookmark_border_24))

                finalArr.add(newsUiElement)
            }
        }
        return finalArr
    }
}