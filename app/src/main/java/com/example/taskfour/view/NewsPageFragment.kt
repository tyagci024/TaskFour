package com.example.taskfour.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskfour.adapter.NewsAdapter
import com.example.taskfour.databinding.FragmentNewsPageBinding
import com.example.taskfour.viewModel.NewsListViewModel

class NewsPageFragment : Fragment() {
    private lateinit var binding: FragmentNewsPageBinding
    private val viewModelNews: NewsListViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsPageBinding.inflate(inflater, container, false)
        binding.recyclerViewNews.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsObserver()
        refreshApiData()
    }

    private fun openLinkInBrowser(link: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

    private fun newsObserver() {
        viewModelNews.newsList.observe(viewLifecycleOwner) { newsList ->
            adapter = NewsAdapter(newsList)
            binding.recyclerViewNews.adapter = adapter
            adapter.onItemClickListener = { sourceLink ->
                openLinkInBrowser(sourceLink)
            }
        }
        viewModelNews.loadingObs.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    progressBar.visibility = View.VISIBLE
                    recyclerViewNews.visibility = View.GONE
                    textviewError.visibility = View.GONE
                }
            } else {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewNews.visibility = View.VISIBLE
                    textviewError.visibility = View.GONE
                }
            }
        }
        viewModelNews.errorObs.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewNews.visibility = View.GONE
                    textviewError.visibility = View.VISIBLE
                }
            } else {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewNews.visibility = View.VISIBLE
                    textviewError.visibility = View.GONE
                }
            }
        }
    }

    private fun refreshApiData() {
        binding.swipeRefreshLay.setOnRefreshListener {
            viewModelNews.getDataFromAPi()
            binding.swipeRefreshLay.isRefreshing = false
        }
    }
}