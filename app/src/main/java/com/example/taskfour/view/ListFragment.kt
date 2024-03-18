package com.example.taskfour.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskfour.adapter.Adapter
import com.example.taskfour.databinding.FragmentListBinding
import com.example.taskfour.viewModel.ListViewModel

class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapterCoin: Adapter
    private lateinit var binding: FragmentListBinding

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
    }

    fun liveDataObserver(){
        viewModel.cryptoListObs.observe(viewLifecycleOwner) {
            it?.let {
                adapterCoin = Adapter(it)
                binding.recyclerViewCrypto.adapter = adapterCoin
                adapterCoin.onItemClickListener = { cryptoModel ->
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(cryptoModel)
                    findNavController().navigate(action)
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
