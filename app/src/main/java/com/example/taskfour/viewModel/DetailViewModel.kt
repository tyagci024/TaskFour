package com.example.taskfour.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.repository.TaskFourRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val repository: TaskFourRepository
) : AndroidViewModel(application) {
    var allDataFavorite: LiveData<List<CryptoModel>>
    private val firestore = FirebaseFirestore.getInstance()

    init {
        allDataFavorite = repository.getAllCrypto()
    }
    fun isCoinlInDatabase(symbol: String): LiveData<Boolean> {
        return repository.isCoinInDatabase(symbol)
    }

    fun insertCrypto(crypto: CryptoModel) {
        viewModelScope.launch {
            repository.insertCrypto(crypto)
        }
    }

    fun deleteCrypto(symbol: String) {
        viewModelScope.launch {
            repository.deleteById(symbol)
        }
    }

    fun addCoinToFirestore(crypto: CryptoModel) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val coinData = hashMapOf(
                "coinid" to crypto.coinId,
                "id" to crypto.id,
                "name" to crypto.name,
                "symbol" to crypto.symbol.uppercase(),
                "price" to crypto.currentPrice.toString(),
                "high24h" to crypto.high24h.toString(),
                "low24h" to crypto.low24h.toString(),
                "lastupdate" to crypto.lastUpdated,
                "priceChange" to crypto.priceChangePercentage24H,
                "image" to crypto.image,
                "timestamp" to SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            )

            firestore.collection("kullanıcılar").document(user.email.toString())
                .collection("coins").document(crypto.id)
                .set(coinData)
                .addOnSuccessListener {
                    println("Coin başarıyla Firestore'a eklendi.")
                }
                .addOnFailureListener { e ->
                    println("${e.message}")
                }
        } ?: run {
            println("Kullanıcı oturum açmamış, Firestore'a ekleme işlemi gerçekleştirilemedi.")
        }
    }
}