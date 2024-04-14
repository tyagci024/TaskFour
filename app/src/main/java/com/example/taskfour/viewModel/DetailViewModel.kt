package com.example.taskfour.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.repository.TaskFourRepository
import com.example.taskfour.room.CryptoDatabase
import com.example.taskfour.room.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(application: Application, private val repository: TaskFourRepository) : AndroidViewModel(application) {
    var allDataFavorite: LiveData<List<CryptoModel>>
    init {
        allDataFavorite = repository.getAllCrypto()
    }

    fun isCoinlInDatabase(coinId: Int): LiveData<Boolean> {
        return repository.isCoinInDatabase(coinId)
    }

    fun insertCrypto(crypto: CryptoModel) {
        viewModelScope.launch {
            repository.insertCrypto(crypto)
        }
    }

    fun deleteCrypto(coinId: Int) {
        viewModelScope.launch {
            repository.deleteById(coinId)
        }
    }
}