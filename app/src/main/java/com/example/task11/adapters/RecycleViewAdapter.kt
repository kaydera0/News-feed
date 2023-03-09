package com.example.task11.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task11.databinding.NewsUiElementBinding
import com.example.task11.uiElements.NewsUiElement
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso


class RecycleViewAdapter(val uiElementArray: ArrayList<NewsUiElement>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
//class RecycleViewAdapter() : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

//    val uiElementArray = ArrayList<NewsUiElement>()
    private lateinit var binding: NewsUiElementBinding

    class ViewHolder(val binding: NewsUiElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(newsUiElement: NewsUiElement) {
//            binding.imageNews.setImageURI(newsUiElement.image)
            Picasso.get().load(newsUiElement.image).into(binding.imageNews)
            binding.dateNews.text = newsUiElement.date
            binding.tittleNews.text = newsUiElement.title
            binding.sourceNews.text = newsUiElement.source
            binding.favNews.isActivated = newsUiElement.isFavorite
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


