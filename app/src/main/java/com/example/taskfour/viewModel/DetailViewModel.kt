package com.example.taskfour.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.CryptoModel
import com.example.taskfour.room.CryptoDatabase
import com.example.taskfour.room.CryptoRepository
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    var readAllData: LiveData<List<CryptoModel>>
    var repository: CryptoRepository
    init {
        val cryptoDao = CryptoDatabase.getDatabase(application).cryptoDao()
        repository = CryptoRepository(cryptoDao)
        readAllData = repository.readAllData
    }

    fun isSymbolInDatabase(symbol: String): LiveData<Boolean> {
        return repository.isSymbolInDatabase(symbol)
    }

    fun insertCrypto(crypto: CryptoModel) {
        viewModelScope.launch {
            repository.insertCrypto(crypto)
        }
    }

    fun deleteCrypto(symbol: String) {
        viewModelScope.launch {
            repository.deleteBySymbol(symbol)
        }
    }
}