package com.example.taskfour.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.room.CryptoDao
import com.example.taskfour.room.CryptoDatabase
import com.example.taskfour.room.CryptoRepository
import com.example.taskfour.service.CryptoApi
import com.example.taskfour.service.CryptoApiService
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {
    var readAllData: LiveData<List<CryptoModel>>
    var repostory: CryptoRepository

    private val cryptoApiService = CryptoApiService()

    private val cryptoList = MutableLiveData<List<CryptoModel>>()
    val cryptoListObs: LiveData<List<CryptoModel>>
        get() = cryptoList
    private val loading = MutableLiveData<Boolean>()
    val loadingObs: LiveData<Boolean>
        get() = loading
    private val error = MutableLiveData<String>()
    val errorObs: LiveData<String>
        get() = error

    init {
        fetchData()
        val cryptoDao = CryptoDatabase.getDatabase(application).cryptoDao()
        repostory = CryptoRepository(cryptoDao)
        readAllData = cryptoDao.getAllCryptos()
    }
    fun insertCrypto(crypto: CryptoModel) {
        viewModelScope.launch {
            repostory.insertCrypto(crypto)
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = cryptoApiService.getCoin()
                cryptoList.value = result
                error.value = ""
            } catch (e: Exception) {
                error.value = "Error: ${e.message}"
            }
            loading.value = false
        }
    }
    fun logAllCryptos() {
        viewModelScope.launch {
            val cryptos = readAllData
            cryptos.value?.forEach {
                Log.d("CryptoLog", it.name) // Ya da başka bir log işlemi
            }
        }
    }
}


