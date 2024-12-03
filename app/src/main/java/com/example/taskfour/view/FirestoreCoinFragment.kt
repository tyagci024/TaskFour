package com.example.taskfour.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskfour.R
import com.example.taskfour.adapter.Adapter
import com.example.taskfour.databinding.FragmentFavoritesPageBinding
import com.example.taskfour.databinding.FragmentFirestoreCoinBinding
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.viewModel.CoinListViewModel
import com.example.taskfour.viewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirestoreCoinFragment : Fragment() {
    private lateinit var binding: FragmentFirestoreCoinBinding
    private lateinit var adapter: Adapter
    private val viewModelFire: FirestoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFirestoreCoinBinding.inflate(inflater, container, false)
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(requireContext())
        var dividerItemDecoration= DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources,R.drawable.divider_drable, null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerViewFav.addItemDecoration(dividerItemDecoration)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFire.fetchUserCoinsFromFirestore()
            .observe(viewLifecycleOwner) { userCoins ->
                adapter = Adapter(userCoins)
                binding.recyclerViewFav.adapter = adapter
            }
    }
}
