package com.example.task11.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task11.adapters.RecycleViewAdapter
import com.example.task11.databinding.FragmentFavoritesBinding
import com.example.task11.databinding.FragmentNewslineBinding
import com.example.task11.uiElements.NewsUiElement
import com.example.task11.viewModels.NewslineViewModel
import com.example.task11.viewModels.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val vm: NewslineViewModel by viewModels()
    private lateinit var adapter: RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        val defaultArr = ArrayList<NewsUiElement>()
        adapter = RecycleViewAdapter(defaultArr)
        binding.recycleViewFavorites.adapter = adapter
        binding.recycleViewFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewFavorites.setHasFixedSize(true)
        binding.recycleViewFavorites.itemAnimator = DefaultItemAnimator()



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}