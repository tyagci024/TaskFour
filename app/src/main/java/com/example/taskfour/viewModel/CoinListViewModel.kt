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
class CoinListViewModel @Inject constructor(application: Application, private val repository: TaskFourRepository) : AndroidViewModel(application) {
    var allDataFavorite: LiveData<List<CryptoModel>>

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
        allDataFavorite = repository.getAllCrypto()
    }//yükleme olana kadar loading
    fun fetchData() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repository.getAllCoin()
                val allCrypto = allDataFavorite.value
                Log.d("CoinListViewModel", "Fetched data size: ${result.size}")

                for (apiCrypto in result) {
                    val matchingCrypto = allCrypto?.find { it.id == apiCrypto.id }
                    matchingCrypto?.let {
                        apiCrypto.id = it.id
                        repository.updateCrypto(apiCrypto)
                    }
                }
                cryptoList.value = result
                error.value = "false"
            } catch (e: Exception) {
                error.value = e.toString()
            }
            loading.value = false
        }
    }
}