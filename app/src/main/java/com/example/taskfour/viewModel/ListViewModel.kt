package com.example.taskfour.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.service.CryptoApi
import com.example.taskfour.service.CryptoApiService
import kotlinx.coroutines.launch

class ListViewModel() : ViewModel() {
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
}

