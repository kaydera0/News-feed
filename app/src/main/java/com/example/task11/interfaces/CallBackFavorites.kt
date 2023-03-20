package com.example.task11.interfaces

import com.example.task11.uiElements.NewsUiElement

interface CallBackFavorites {
    fun addToFavorites(newsUiElement: NewsUiElement)
    fun removeFromFavorites(newsUiElement: NewsUiElement)
}