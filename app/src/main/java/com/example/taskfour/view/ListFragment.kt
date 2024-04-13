package com.example.taskfour.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.taskfour.databinding.FragmentListBinding
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.viewModel.CoinListViewModel
import com.example.taskfour.viewModel.NewsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: CoinListViewModel by viewModels()
    private lateinit var adapterCoin: Adapter
    private lateinit var binding: FragmentListBinding
    private lateinit var originalList: List<CryptoModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerViewCrypto.layoutManager = LinearLayoutManager(requireContext())
        var dividerItemDecoration= DividerItemDecoration(requireContext(),RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources,R.drawable.divider_drable,null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerViewCrypto.addItemDecoration(dividerItemDecoration)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        liveDataObserver()
        refreshApiData()
        setSearchBar()
    }

    fun liveDataObserver() {
        viewModel.cryptoListObs.observe(viewLifecycleOwner) {
            it?.let {
                originalList = it
                adapterCoin = Adapter(it)
                binding.recyclerViewCrypto.adapter = adapterCoin
                adapterCoin.onItemClickListener = { cryptoModel ->
                    val action =
                        ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                    findNavController().navigate(action)
                }
            }
        }
        viewModel.loadingObs.observe(viewLifecycleOwner) {
            if (it) {
                with(binding) {
                    progressBar.visibility = View.VISIBLE
                    recyclerViewCrypto.visibility = View.GONE
                    textviewError.visibility = View.GONE
                }
            } else {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewCrypto.visibility = View.VISIBLE
                    textviewError.visibility = View.GONE
                }
            }
        }
        viewModel.errorObs.observe(viewLifecycleOwner) {
            if (it=="false") {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewCrypto.visibility = View.VISIBLE
                    textviewError.visibility = View.GONE
                }
            } else {
                with(binding) {
                    progressBar.visibility = View.GONE
                    recyclerViewCrypto.visibility = View.GONE
                    textviewError.visibility = View.VISIBLE
                    textviewError.text=it
                }
            }
        }
    }

    private fun setSearchBar(){
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
    private fun refreshApiData(){
        binding.swipeRefreshLay.setOnRefreshListener {
            viewModel.fetchData()
            binding.swipeRefreshLay.isRefreshing = false
        }
    }
}
