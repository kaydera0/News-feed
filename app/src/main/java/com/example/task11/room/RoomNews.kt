package com.example.task11.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.task11.uiElements.NewsUiElement

@Entity(tableName = "news")
data class RoomNews(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val image: String?,
    val date: String,
    val title: String,
    val source: String,
    val link: String?
) {
    companion object {
        fun toRoomNews(newsUiElement: NewsUiElement): RoomNews = RoomNews(
            id = 0,
            image = newsUiElement.image.toString(),
            date = newsUiElement.date,
            title = newsUiElement.title,
            source = newsUiElement.source,
            link = newsUiElement.link

        )
    }
}