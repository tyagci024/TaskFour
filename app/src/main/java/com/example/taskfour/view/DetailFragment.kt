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
            textViewCoinSymbol.text=args.currentCoin.symbol.uppercase()
            textViewPrice.text="$ "+args.currentCoin.currentPrice.toString()
            textViewHigh24h.text=args.currentCoin.high24h.toString()
            textViewLow24h.text=args.currentCoin.low24h.toString()
            viewModel.isSymbolInDatabase(args.currentCoin.symbol).observe(viewLifecycleOwner) { isSymbolInDatabase ->
                if (isSymbolInDatabase) {
                    args.currentCoin.fav=true
                    Log.d("symbol","${isSymbolInDatabase}")
                    imageViewFavIcon.setImageResource(R.drawable.enabled_fav_star)
                    imageViewFavIcon.setOnClickListener {
                        viewModel.deleteCrypto(args.currentCoin.symbol)
                        args.currentCoin.fav=false

                    }
                } else {

                    imageViewFavIcon.setImageResource(R.drawable.star)
                    imageViewFavIcon.setOnClickListener {
                        viewModel.insertCrypto(args.currentCoin)
                    }                }
            }
        }
        binding.imageViewCoin.downloadFromURL(args.currentCoin.image)
    }
}