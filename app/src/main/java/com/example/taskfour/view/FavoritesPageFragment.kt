package com.example.taskfour.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskfour.R
import com.example.taskfour.adapter.Adapter
import com.example.taskfour.adapter.NewsAdapter
import com.example.taskfour.databinding.FragmentFavoritesPageBinding
import com.example.taskfour.viewModel.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesPageFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesPageBinding
    private lateinit var adapter: Adapter
    private val viewModel: CoinListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavoritesPageBinding.inflate(inflater, container, false)
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drable, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerViewFav.addItemDecoration(dividerItemDecoration)
        newsObserver()
    }

    private fun newsObserver() {
        viewModel.allDataFavorite.observe(viewLifecycleOwner) { newsList ->
            adapter = Adapter(newsList)
            binding.recyclerViewFav.adapter = adapter
            adapter.onItemClickListener = { cryptoModel ->
                val action =
                    FavoritesPageFragmentDirections.actionFavoritesPageFragmentToDetailFragment(
                        cryptoModel)
                findNavController().navigate(action)
            }
        }
    }
}
