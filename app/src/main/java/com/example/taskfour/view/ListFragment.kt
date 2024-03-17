package com.example.taskfour.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.taskfour.R
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.service.CryptoApi
import com.example.taskfour.viewModel.ListViewModel


class ListFragment : Fragment() {
    private val viewModel: ListViewModel by viewModels()
    private val cryptoList = MutableLiveData<List<CryptoModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        return inflater.inflate(R.layout.fragment_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // CryptoListLiveData'nın gözlemlenmesi
        viewModel.cryptoListObs.observe(viewLifecycleOwner, Observer { cryptoList ->
            // LiveData'nın değeri değiştiğinde çalışacak kod
            cryptoList?.let {
                // Verileri loga yazdırma
                for (crypto in it) {
                    Log.d("CryptoData", "${crypto.name}: ${crypto.currentPrice}")
                }
            }
        })
    }
}
