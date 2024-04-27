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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirestoreCoinFragment : Fragment() {
    private lateinit var binding:FragmentFirestoreCoinBinding
    private lateinit var adapter: Adapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentFirestoreCoinBinding.inflate(inflater,container,false)
        binding.recyclerViewFav.layoutManager = LinearLayoutManager(requireContext())
        var dividerItemDecoration= DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources,R.drawable.divider_drable,null)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.recyclerViewFav.addItemDecoration(dividerItemDecoration)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUserCoinsFromFirestore()
            .observe(viewLifecycleOwner) { userCoins ->
                adapter = Adapter(userCoins)
                binding.recyclerViewFav.adapter = adapter
                println("Firestore'dan alınan coin listesi: $userCoins")
            }
    }

    fun fetchUserCoinsFromFirestore(): LiveData<List<CryptoModel>> {
        val currentUser = FirebaseAuth.getInstance().currentUser

        val userCoinsLiveData = MutableLiveData<List<CryptoModel>>()

        currentUser?.let { user ->
            firestore.collection("kullanıcılar").document(user.email.toString())
                .collection("coins")
                .get()
                .addOnSuccessListener { documents ->
                    val userCoinsList = mutableListOf<CryptoModel>()
                    for (document in documents) {
                        val coinData = document.data
                        val crypto = createCryptoModelFromCoinData(coinData)
                        crypto?.let {
                            userCoinsList.add(it)
                        }
                    }
                    userCoinsLiveData.value = userCoinsList
                }
                .addOnFailureListener { e ->
                    println("Firestore'dan coins çekerken bir hata oluştu: ${e.message}")
                }
        } ?: run {
            println("Kullanıcı oturum açmamış, Firestore'dan veri çekme işlemi gerçekleştirilemedi.")
        }

        return userCoinsLiveData
    }

    fun createCryptoModelFromCoinData(coinData: Map<String, Any>?): CryptoModel? {
        coinData?.let { data ->
            return try {
                CryptoModel(
                    coinId = 0,
                    id = data["id"] as String,
                    name = data["name"] as String,
                    symbol = (data["symbol"] as String).uppercase(),
                    currentPrice = (data["price"] as String).toDouble(),
                    high24h = (data["high24h"] as String).toDouble(),
                    low24h = (data["low24h"] as String).toDouble(),
                    lastUpdated = data["lastupdate"] as String,
                    priceChangePercentage24H = data["priceChange"] as Double,
                    image = data["image"] as String)
            } catch (e: Exception) {
                println("Coin verilerini oluştururken bir hata oluştu: ${e.message}")
                null
            }
        } ?: run {
            println("Coin verileri boş veya null.")
            return null
        }
    }
}