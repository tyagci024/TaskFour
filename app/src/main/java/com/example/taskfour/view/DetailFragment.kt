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
import com.example.taskfour.viewModel.DetailViewModel
import com.example.taskfour.viewModel.ListViewModel

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel: DetailViewModel by viewModels()

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
            textViewCoinName.text = args.currentCoin.name
            textViewCoinSymbol.text = args.currentCoin.symbol.uppercase()
            textViewPrice.text = StringBuilder().append("$").append(args.currentCoin.currentPrice).toString()
            textViewHigh24h.text = args.currentCoin.high24h.toString()
            textViewLow24h.text = args.currentCoin.low24h.toString()
            viewModel.isSymbolInDatabase(args.currentCoin.symbol).observe(viewLifecycleOwner) { isSymbolInDatabase ->
                if (isSymbolInDatabase) {
                    imageViewFavIcon.setImageResource(R.drawable.enabled_fav_star)
                    imageViewFavIcon.setOnClickListener {
                        viewModel.deleteCrypto(args.currentCoin.symbol)
                    }
                } else {
                    imageViewFavIcon.setImageResource(R.drawable.star)
                    imageViewFavIcon.setOnClickListener {
                        viewModel.insertCrypto(args.currentCoin)
                    }
                }
            }
           /* imageViewFavIcon.setOnClickListener {
                if (args.currentCoin.fav == true) {
                    imageViewFavIcon.setImageResource(R.drawable.star)
                    args.currentCoin.fav = false
                    viewModel.deleteCrypto(args.currentCoin.symbol)
                } else {
                    imageViewFavIcon.setImageResource(R.drawable.enabled_fav_star)
                    args.currentCoin.fav = true
                    viewModel.insertCrypto(args.currentCoin)
                    viewModel.readAllData.observe(viewLifecycleOwner, Observer { cryptos ->
                        cryptos.forEach { crypto ->
                            Log.d("CryptoItem", "ID: ${crypto.coinId}, Name: ${crypto.name}, Symbol: ${crypto.symbol}")
                        }
                    })
                }
            }*/
        }
        binding.imageViewCoin.downloadFromURL(args.currentCoin.image)
    }
}