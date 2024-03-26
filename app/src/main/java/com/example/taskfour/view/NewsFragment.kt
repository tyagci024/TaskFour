package com.example.taskfour.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskfour.adapter.NewsAdapter
import com.example.taskfour.databinding.FragmentNewBinding
import com.example.taskfour.viewModel.NewsListViewModel

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewBinding
    private val viewModelNews: NewsListViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewBinding.inflate(inflater, container, false)
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelNews.newsList.observe(viewLifecycleOwner) { newsList ->
            adapter = NewsAdapter(newsList)
            binding.recyclerViewNews.adapter = adapter
            adapter.onItemClickListener = { sourceLink ->
                openLinkInBrowser(sourceLink)
            }
        }
        viewModelNews.getDataFromAPi()
    }

    private fun openLinkInBrowser(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }
}
