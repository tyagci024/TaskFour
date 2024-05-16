package com.example.taskfour.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.repository.TaskFourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    application: Application,
    private val repository: TaskFourRepository
) : AndroidViewModel(application) {
    var allDataFavorite: LiveData<List<CryptoModel>> = repository.getAllCrypto()
    private val cryptoList = MutableLiveData<List<CryptoModel>>()
    val cryptoListObs: LiveData<List<CryptoModel>>
        get() = cryptoList
    private val loading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<String>()
    val errorObs: LiveData<String>
        get() = error

    init {
        fetchAllData(1)
    }

    fun fetchAllData(page: Int, callback: ((Boolean) -> Unit)? = null) {
        viewModelScope.launch {
            try {
                val response = repository.fetchAllData(page)
                val allCrypto = allDataFavorite.value
                val coinList = response
                val currentCoins = cryptoList.value ?: emptyList()
                val updatedCoinssList = currentCoins.toMutableList()
                updatedCoinssList.addAll(coinList)
                cryptoList.value = updatedCoinssList
                callback?.invoke(true)
                for (apiCrypto in response) {
                    val matchingCrypto = allCrypto?.find { it.id == apiCrypto.id }
                    matchingCrypto?.let {
                        apiCrypto.id = it.id
                        repository.updateCrypto(apiCrypto)
                    }
                }
            } catch (e: Exception) {
                error.value = "Veriler yüklenirken bir hata oluştu: ${e.message}"
                callback?.invoke(false)
            }
        }
    }
}