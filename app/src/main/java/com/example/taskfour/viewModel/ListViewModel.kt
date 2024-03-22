package com.example.taskfour.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.room.CryptoDatabase
import com.example.taskfour.room.CryptoRepository
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
    private val error = MutableLiveData<Boolean>()
    val errorObs: LiveData<Boolean>
        get() = error

    init {
        fetchData()
        val cryptoDao = CryptoDatabase.getDatabase(application).cryptoDao()
        repostory = CryptoRepository(cryptoDao)
        readAllData = repostory.readAllData
    }
    fun fetchData() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = cryptoApiService.getCoinList()

                val allCrypto = repostory.getAllCrypto()

                  for (apiCrypto in result) {
                      for (roomCrypto in allCrypto) {
                          if (apiCrypto.symbol == roomCrypto.symbol) {
                              apiCrypto.coinId = roomCrypto.coinId
                              repostory.updateCrypto(apiCrypto) // Güncelleme işlemi
                              break
                          }
                      }
                  }
                cryptoList.value = result
                error.value = false
            } catch (e: Exception) {
                error.value = true
            }
            loading.value = false
        }
    }

}