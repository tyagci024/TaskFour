package com.example.taskfour.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            textViewCoinName.text=args.currentCoin.name
            textViewCoinSymbol.text=args.currentCoin.symbol
            textViewPrice.text=args.currentCoin.currentPrice.toString()
            textViewHigh24h.text=args.currentCoin.high24h.toString()
            textViewLow24h.text=args.currentCoin.low24h.toString()
            textViewLastUpdated.text=args.currentCoin.lastUpdated
        }
        if (args.currentCoin.fav==true){
            binding.imageViewFavIcon.setImageResource(R.drawable.enabled_fav_star)
            binding.imageViewFavIcon.setOnClickListener {
                args.currentCoin.fav=false
                viewModel.deleteCrypto(args.currentCoin.symbol)
            }
                  }
        else{
            binding.imageViewFavIcon.setOnClickListener {
                viewModel.insertCrypto(args.currentCoin)
                args.currentCoin.fav= true
                viewModel.readAllData.observe(viewLifecycleOwner, Observer { cryptos ->
                    cryptos.forEach { crypto ->
                        Log.d("CryptoItem", "ID: ${crypto.coinId}, Name: ${crypto.name}, Symbol: ${crypto.symbol}")
                    }
                })
            }
            binding.imageViewFavIcon.setImageResource(R.drawable.star)

        }
        binding.imageViewCoin.downloadFromURL(args.currentCoin.image)
    }
}