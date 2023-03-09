package com.example.task11.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task11.R
import com.example.task11.adapters.RecycleViewAdapter
import com.example.task11.databinding.FragmentNewslineBinding
import com.example.task11.retrofit.RetrofitApi
import com.example.task11.uiElements.NewsUiElement
import com.example.task11.viewModels.NewslineViewModel
import com.google.gson.GsonBuilder
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewslineFragment : Fragment() {

    private var _binding: FragmentNewslineBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm:NewslineViewModel
    private lateinit var adapter: RecycleViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewslineBinding.inflate(inflater, container, false)

        val defaultArr = ArrayList<NewsUiElement>()
        defaultArr.add(NewsUiElement(null,"date","title","source1",true))

        adapter = RecycleViewAdapter(defaultArr)


        binding.recycleView.adapter = adapter
//        binding.recycleView.layoutManager = LinearLayoutManager(context)
//        binding.recycleView.layoutManager = GridLayoutManager(context,3)
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.itemAnimator = DefaultItemAnimator()
        adapter.notifyDataSetChanged()
//        adapter.newsArray.add(NewsUiElement(null,"date","title","source",true))

        vm = NewslineViewModel()

        vm.news.observe(viewLifecycleOwner, Observer {
            Log.d("MY_TAG",it.size.toString())
            adapter = RecycleViewAdapter(vm.convert(it))
            binding.recycleView.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        binding.swipeContainer.setColorSchemeResources(R.color.black, R.color.orange)
        binding.swipeContainer.canChildScrollUp()
        binding.swipeContainer.setOnRefreshListener {

            binding.swipeContainer.isRefreshing = true
            Log.d("RETRO_TAG","REFRESH")
            binding.swipeContainer.isRefreshing = false
        }

        vm.retroFinal()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}