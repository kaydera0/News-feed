package com.example.task11.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.task11.R
import com.example.task11.databinding.FragmentFiltersBinding
import com.example.task11.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiltersFragment : Fragment() {

    private val vm: MainViewModel by activityViewModels()
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (vm.redditIsChecked.value!!){
            binding.redditBtn.background = resources.getDrawable(R.drawable.reddit_background)
        }else{
            binding.redditBtn.background = resources.getDrawable(R.drawable.grey_background)
        }
        if (vm.feedIsChecked.value!!){
            binding.feedsBtn.background = resources.getDrawable(R.drawable.feedburner_background)
        }else{
            binding.feedsBtn.background = resources.getDrawable(R.drawable.grey_background)
        }

        binding.feedsBtn.setOnClickListener {
            if (vm.feedIsChecked.value!!) {
                binding.feedsBtn.background =
                    resources.getDrawable(R.drawable.feedburner_background)
                vm.feedIsChecked.value = false
            } else {
                binding.feedsBtn.background = resources.getDrawable(R.drawable.grey_background)
                vm.feedIsChecked.value = true
            }
        }
        binding.redditBtn.setOnClickListener {
            if (vm.redditIsChecked.value!!) {
                binding.redditBtn.background =
                    resources.getDrawable(R.drawable.reddit_background)
                vm.redditIsChecked.value = false
            } else {
                binding.redditBtn.background = resources.getDrawable(R.drawable.grey_background)
                vm.redditIsChecked.value = true
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}