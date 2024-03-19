package com.example.taskfour.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.taskfour.BR
import com.example.taskfour.R
import com.example.taskfour.databinding.FragmentDetailBinding
import com.example.taskfour.utilies.downloadFromURL
import com.example.taskfour.viewModel.ListViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        with(binding){
            textViewCoinName.text=args.currentCoin.name
            textViewCoinSymbol.text=args.currentCoin.symbol
            textViewPrice.text=args.currentCoin.currentPrice.toString()
            textViewHigh24h.text=args.currentCoin.high24h.toString()
        }
        binding.imageViewFavIcon.setOnClickListener {
            viewModel.insertCrypto(args.currentCoin)
            viewModel.readAllData.observe(viewLifecycleOwner, Observer { cryptos ->
                // Log the items stored in the database
                cryptos.forEach { crypto ->
                    Log.d("CryptoItem", "ID: ${crypto.id}, Name: ${crypto.name}, Symbol: ${crypto.symbol}")
                }
            })
        }
        binding.imageViewCoin.downloadFromURL(args.currentCoin.image)

        return binding.root
    }
}
