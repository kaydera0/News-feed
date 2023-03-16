package com.example.task11.uiElements

import android.graphics.drawable.Drawable
import android.net.Uri

data class NewsUiElement(
    val image: Uri?,
    val date: String,
    val title: String,
    val source: String,
    val drawable: Drawable?,
    val favoriteCheck: Drawable?,
    val favoriteUnCheck: Drawable?,
    val link :String?,
    var isFavorite:Boolean
    )