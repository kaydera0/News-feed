package com.example.task11.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.task11.R
import com.example.task11.databinding.NewsUiElementBinding
import com.example.task11.uiElements.NewsUiElement
import com.squareup.picasso.Picasso


class RecycleViewAdapter(val uiElementArray: List<NewsUiElement>) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {


    private lateinit var binding: NewsUiElementBinding
    var fawNews = favoriteNews
    class ViewHolder(val binding: NewsUiElementBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(newsUiElement: NewsUiElement) {
            if (newsUiElement.image!=null){
                            Picasso.get().load(newsUiElement.image).into(binding.imageNews)
            }
            binding.dateNews.text = newsUiElement.date
            binding.tittleNews.text = newsUiElement.title
            binding.tittleNews.setOnClickListener {
                view->
                val bundle = bundleOf("url" to newsUiElement.link)
                view.findNavController().navigate(R.id.action_navigation_home_to_webViewFragment,bundle)
            }
            binding.sourceNews.text = newsUiElement.source
            binding.sourceNews.background = newsUiElement.drawable
            binding.favNews.background = when(newsUiElement.isFavorite){
                true->newsUiElement.favoriteCheck
                else->newsUiElement.favoriteUnCheck
            }
            binding.favNews.setOnClickListener {
                if (binding.favNews.background == newsUiElement.favoriteUnCheck){
                binding.favNews.background = newsUiElement.favoriteCheck
                    newsUiElement.isFavorite =true
                favoriteNews.add(newsUiElement)}
                else
                    binding.favNews.background = newsUiElement.favoriteUnCheck
                
            }
        }

    }
    companion object{
        val favoriteNews = ArrayList<NewsUiElement>()
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


