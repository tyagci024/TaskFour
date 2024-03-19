package com.example.taskfour.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.taskfour.BR
import com.example.taskfour.R
import com.example.taskfour.databinding.FragmentDetailBinding
import com.example.taskfour.utilies.downloadFromURL

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.setVariable(BR.Coin,args.currentCoin)
        binding.executePendingBindings()


        binding.imageViewCoin.downloadFromURL(args.currentCoin.image)

        return binding.root
    }
}
