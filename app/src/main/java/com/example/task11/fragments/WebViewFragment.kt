package com.example.task11.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.task11.R
import com.example.task11.databinding.FragmentWebViewBinding
import com.example.task11.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private val vm: MainViewModel by activityViewModels()
    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWebViewBinding.inflate(inflater, container, false)

        val url = arguments?.getString("url")

//        binding.webView.loadUrl(url!!)
        binding.webView.loadUrl("http://www.43folders.com/2011/10/17/instapaper-4")
        binding.webView.getSettings().domStorageEnabled = true
        binding.webView.getSettings().cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        binding.webView.getSettings().javaScriptEnabled = true
        binding.webView.getSettings().loadWithOverviewMode = true
        binding.webView.getSettings().useWideViewPort = true

        binding.imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_webViewFragment3_to_mainFragment2)
        }

        return binding.root
    }
}