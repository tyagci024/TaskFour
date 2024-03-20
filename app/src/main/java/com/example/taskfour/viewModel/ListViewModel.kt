package com.example.taskfour.viewModel

import android.app.Application
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
        readAllData = cryptoDao.getAllCryptos()
    }

    fun insertCrypto(crypto: CryptoModel) {
        viewModelScope.launch {
            repostory.insertCrypto(crypto)
        }
    }
    fun deleteCrypto(symbol:String){
        viewModelScope.launch {
            repostory.deleteBySymbol(symbol)
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = cryptoApiService.getCoin()
                result.forEach { crypto ->
                    updateIfExist(crypto)
                }
                cryptoList.value = result
                error.value = false
            } catch (e: Exception) {
                error.value = true
            }
            loading.value = false
        }
    }

    private fun updateIfExist(crypto: CryptoModel) {//burda roomdaki verileri güncellemeye çalışaıyoruz
        viewModelScope.launch {
            val existingCrypto = repostory.getCryptoBySymbol(crypto.symbol)
            existingCrypto?.let {
                repostory.updateCrypto(crypto)
            }
        }
    }
}