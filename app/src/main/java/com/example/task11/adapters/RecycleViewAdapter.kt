package com.example.task11.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.task11.R
import com.example.task11.TestInterface
import com.example.task11.databinding.NewsUiElementBinding
import com.example.task11.uiElements.NewsUiElement
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ApplicationContext


class RecycleViewAdapter(val uiElementArray: List<NewsUiElement>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    private lateinit var binding: NewsUiElementBinding
    val favoriteNews = ArrayList<NewsUiElement>()

    class ViewHolder(val binding: NewsUiElementBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(newsUiElement: NewsUiElement) {
            if (newsUiElement.image!=null){
                            Picasso.get().load(newsUiElement.image).into(binding.imageNews)
            }
            binding.dateNews.text = newsUiElement.date
            binding.tittleNews.text = newsUiElement.title
            binding.sourceNews.text = newsUiElement.source
            binding.sourceNews.background = newsUiElement.drawable
            binding.favNews.background = newsUiElement.favoriteUnCheck
            binding.favNews.setOnClickListener {
                if (binding.favNews.background == newsUiElement.favoriteUnCheck)
                binding.favNews.background = newsUiElement.favoriteCheck

                else
                    binding.favNews.background = newsUiElement.favoriteUnCheck
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NewsUiElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return uiElementArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(uiElementArray[position])


    }
}


