package com.example.task11.uiElements

import android.media.Image
import android.net.Uri

data class NewsUiElement(val image: Uri?, val date:String, val title:String,val source :String, val isFavorite:Boolean)