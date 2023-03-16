package com.example.task11.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities =[RoomNews::class],
    version =1
)
abstract class RoomDB:RoomDatabase() {
    abstract fun roomNewsDao():RoomNewsDao?

    companion object{
        val DB_NAME ="database-name"
    }
}