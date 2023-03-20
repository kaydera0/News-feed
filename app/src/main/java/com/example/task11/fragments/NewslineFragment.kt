package com.example.task11.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task11.interfaces.CallBackFavorites
import com.example.task11.R
import com.example.task11.adapters.RecycleViewAdapter
import com.example.task11.databinding.FragmentNewslineBinding
import com.example.task11.uiElements.NewsUiElement
import com.example.task11.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewslineFragment : Fragment() {

    private val vm:MainViewModel by activityViewModels()
    private var _binding: FragmentNewslineBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RecycleViewAdapter
    private var testInt : CallBackFavorites? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewslineBinding.inflate(inflater, container, false)

        testInt = object : CallBackFavorites {
            override fun addToFavorites(newsUiElement: NewsUiElement) {
                vm.favNews.value?.add(newsUiElement)
                CoroutineScope(Dispatchers.IO).launch {
                    vm.addElementToDB(newsUiElement)
                }
            }
            override fun removeFromFavorites(newsUiElement: NewsUiElement) {
                vm.favNews.value?.remove(newsUiElement)
                CoroutineScope(Dispatchers.IO).launch {
                    vm.deleteFromDB(newsUiElement)
                }
            }
        }

        val defaultArr = ArrayList<NewsUiElement>()
        adapter = RecycleViewAdapter(defaultArr, testInt as CallBackFavorites)
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.itemAnimator = DefaultItemAnimator()

        vm.news.observe(viewLifecycleOwner, Observer {
            Log.d("MY_TAG",it.size.toString())
            binding.progressBar.visibility = View.INVISIBLE
            adapter = RecycleViewAdapter(it, testInt as CallBackFavorites)
            binding.recycleView.adapter = adapter
        })

        binding.swipeContainer.setColorSchemeResources(R.color.black, R.color.orange)
        binding.swipeContainer.canChildScrollUp()
        binding.swipeContainer.setOnRefreshListener {
            binding.swipeContainer.isRefreshing = true
            vm.callRssNews()
            binding.swipeContainer.isRefreshing = false
        }
        vm.favNews.observe(viewLifecycleOwner, Observer {
            Log.d("MY-TAG","FAV OBSERVER TRIGGERED")
        })

        vm.callRssNews()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}