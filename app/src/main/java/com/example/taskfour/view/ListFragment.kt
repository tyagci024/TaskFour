package com.example.taskfour.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskfour.R
import com.example.taskfour.adapter.Adapter
import com.example.taskfour.databinding.FragmentListBinding
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.viewModel.ListViewModel
import java.util.Locale

class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapterCoin: Adapter
    private lateinit var binding: FragmentListBinding
    private lateinit var originalList: List<CryptoModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerViewCrypto.layoutManager = LinearLayoutManager(requireContext())
        liveDataObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().toLowerCase(Locale.getDefault())
                if (::originalList.isInitialized) {
                    val filteredList = originalList.filter {
                        it.name.toLowerCase(Locale.getDefault()).contains(searchText)
                    }
                    adapterCoin.updateList(filteredList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        // Alt gezinme çubuğunun tıklama olaylarını işleme
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_all -> {
                    // "Hepsi" tıklandığında yapılacak işlemler
                    viewModel.cryptoListObs.observe(viewLifecycleOwner) {
                        it?.let {
                            originalList = it
                            adapterCoin = Adapter(it)
                            binding.recyclerViewCrypto.adapter = adapterCoin
                            adapterCoin.onItemClickListener = { cryptoModel ->
                                val action = ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                                findNavController().navigate(action)
                                viewModel.logAllCryptos()
                            }
                        }
                    }
                    true
                }
                R.id.navigation_favorites -> {
                    // "Favoriler" tıklandığında yapılacak işlemler
                    viewModel.readAllData.observe(viewLifecycleOwner) {
                        it?.let {
                            originalList = it
                            adapterCoin = Adapter(it)
                            binding.recyclerViewCrypto.adapter = adapterCoin
                            adapterCoin.onItemClickListener = { cryptoModel ->
                                val action = ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                                findNavController().navigate(action)
                                viewModel.logAllCryptos()
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    fun liveDataObserver(){
        viewModel.cryptoListObs.observe(viewLifecycleOwner) {
            it?.let {
                originalList = it
                adapterCoin = Adapter(it)
                binding.recyclerViewCrypto.adapter = adapterCoin
                adapterCoin.onItemClickListener = { cryptoModel ->
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                    findNavController().navigate(action)
                    viewModel.logAllCryptos()
                }
            }
        }
        viewModel.loadingObs.observe(viewLifecycleOwner) {
            if (it) {
                with(binding){
                    progressBar.visibility = View.VISIBLE
                    recyclerViewCrypto.visibility = View.GONE
                    textviewError.visibility = View.GONE
                }
            }
            else {
                with(binding){
                    progressBar.visibility = View.GONE
                    recyclerViewCrypto.visibility = View.VISIBLE
                    textviewError.visibility = View.GONE
                }
            }
        }
    }
}
