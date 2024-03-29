package com.example.task11.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomNewsDao {

    @Insert
suspend fun insertNews(roomNews:RoomNews)

@Query("SELECT * FROM news")
suspend fun getNews():List<RoomNews>

@Query("DELETE FROM news")
suspend fun clearTable()

@Query("DELETE FROM news WHERE link = :url")
suspend fun deleteByUrl(url:String)
}