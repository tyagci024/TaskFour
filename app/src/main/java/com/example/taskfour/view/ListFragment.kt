package com.example.taskfour.view

import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskfour.R
import com.example.taskfour.adapter.Adapter
import com.example.taskfour.databinding.FragmentListBinding
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.viewModel.CoinListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var adapterCoin: Adapter
    private lateinit var binding: FragmentListBinding
    private lateinit var originalList: List<CryptoModel>
    private var currentPage = 1
    private var recyclerState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider_drable, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerViewCrypto.addItemDecoration(dividerItemDecoration)
        binding.recyclerViewCrypto.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = Adapter(emptyList())
            var isLoading = false
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!isLoading && lastVisibleItemPosition == totalItemCount - 1) {
                        if (currentPage < 3) {
                            isLoading = true
                            currentPage++
                            viewModel.fetchAllData(currentPage) { success ->
                                isLoading = !success
                            }
                            Log.e("Tag", "$currentPage")
                        }
                    }
                }
            })
        }

        observeViewModel()
        setSearchBar()
    }

    private fun observeViewModel() {
        viewModel.cryptoListObs.observe(viewLifecycleOwner, { coins ->
            // originalList'ı buradan başlatıyoruz
            originalList = coins

            adapterCoin = binding.recyclerViewCrypto.adapter as Adapter
            adapterCoin.onItemClickListener = { cryptoModel ->
                val action = ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                findNavController().navigate(action)
            }
            adapterCoin.updateList(coins)
            binding.recyclerViewCrypto.layoutManager?.onRestoreInstanceState(recyclerState)
        })

        viewModel.errorObs.observe(viewLifecycleOwner, { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setSearchBar() {
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
                val searchText = s.toString().lowercase(Locale.getDefault())
                if (::originalList.isInitialized) {
                    val filteredList = originalList.filter {
                        it.name.lowercase(Locale.getDefault()).contains(searchText)
                    }
                    adapterCoin.updateList(filteredList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}
