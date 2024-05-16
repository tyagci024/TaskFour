package com.example.taskfour.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.repository.TaskFourRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel @Inject constructor(
    application: Application,
    private val repository: TaskFourRepository
) : AndroidViewModel(application) {
    private val firestore = FirebaseFirestore.getInstance()
    val userCoinsLiveData = MutableLiveData<List<CryptoModel>>()

    init {
        fetchUserCoinsFromFirestore()
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
                    image = data["image"] as String,
                )
            } catch (e: Exception) {
                println(" ${e.message}")
                null
            }
        } ?: run {
            println("Coin verileri boş veya null.")
            return null
        }
    }

    fun fetchUserCoinsFromFirestore(): LiveData<List<CryptoModel>> {
        val currentUser = FirebaseAuth.getInstance().currentUser

        currentUser?.let { user ->
            firestore.collection("kullanıcılar").document(user.email.toString())
                .collection("coins")
                .get()
                .addOnSuccessListener { documents ->
                    val userCoinsList = mutableListOf<CryptoModel>()
                    for (document in documents) {
                        val coinData = document.data
                        val crypto = createCryptoModelFromCoinData(coinData)//anladımmm
                        crypto?.let {
                            userCoinsList.add(it)
                        }
                    }
                    viewModelScope.launch {
                        val result = repository.getAllCoin()

                        for (apiCrypto in result) {
                            val matchingCrypto = userCoinsList.find { it.id == apiCrypto.id }
                            matchingCrypto?.let {
                                it.currentPrice = apiCrypto.currentPrice
                            }
                        }
                        userCoinsLiveData.value = userCoinsList
                    }
                }
                .addOnFailureListener { e ->
                    println("${e.message}")
                }
        } ?: run {
            println("Kullanıcı oturum açmamış, Firestore'dan veri çekme işlemi gerçekleştirilemedi.")
        }

        return userCoinsLiveData
    }
}