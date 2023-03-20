package com.example.task11.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.task11.R
import com.example.task11.uiElements.NewsUiElement
import retrofit2.Response

class RssRepository(val context: Context) {

    private var redditUri =
        Uri.parse("https://www.iconpacks.net/icons/2/free-reddit-logo-icon-2436-thumb.png")

    fun sortRedditRss(
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

    fun sortFeedburnerRss(response: Response<String>): ArrayList<NewsUiElement> {
        val titleArr = response.body().toString().split("title>")
        val dateArr = response.body().toString().split("pubDate")
//        val imageArr = response.body().toString().split("url=\"")
        val imageArr = response.body().toString().split("<img src=\"")
        val linkArr = response.body().toString().split("<link>")
        val finalArr = ArrayList<NewsUiElement>()
        var counter = 1
        for (i in 0..17) {
            if (i % 2 == 1 && i >= 3) {
                val title = titleArr[i].replace(">", "").replace("<", "").replace("/", "").replace("![CDATA[","").replace("]]","")
                val date = dateArr[i - 2].replace(">", "").replace("<", "").replace("/", "")
                    .substring(5, 17)
                val image = imageArr[counter].split("\"")[0]
                val link = linkArr[counter + 1].split("</link>")[0]
                Log.d("MY_TAG",link)
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
}