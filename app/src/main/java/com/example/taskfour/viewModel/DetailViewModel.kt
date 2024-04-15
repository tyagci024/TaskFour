package com.example.taskfour.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.repository.TaskFourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(application: Application, private val repository: TaskFourRepository) : AndroidViewModel(application) {
    var allDataFavorite: LiveData<List<CryptoModel>>
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
}