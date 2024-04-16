package com.example.taskfour.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskfour.model.NewsItem
import com.example.taskfour.repository.TaskFourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    application: Application,
    private val repository: TaskFourRepository,
) : AndroidViewModel(application) {
    val newsList = MutableLiveData<List<NewsItem>>()
    private val loading = MutableLiveData<Boolean>()
    val loadingObs: LiveData<Boolean>
        get() = loading
    private val error = MutableLiveData<String>()
    val errorObs: LiveData<String>
        get() = error

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repository.getAllNews()

                newsList.value = result
                error.value = "false"
            } catch (e: Exception) {
                error.value = e.toString()
            }
            loading.value = false
        }
    }
    companion object {
        private const val TAG = "NewsListViewModel"
    }
}
