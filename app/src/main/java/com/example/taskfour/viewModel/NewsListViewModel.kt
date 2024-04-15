package com.example.taskfour.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskfour.model.NewsItem
import com.example.taskfour.repository.TaskFourRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(application: Application, private val repository: TaskFourRepository) : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
    val newsList = MutableLiveData<List<NewsItem>>()
    private val loading = MutableLiveData<Boolean>()
    val loadingObs: LiveData<Boolean>
        get() = loading
    private val error = MutableLiveData<String>()
    val errorObs: LiveData<String>
        get() = error
    init {
        getDataFromAPi()
    }
    fun getDataFromAPi() {
        loading.value = true
        disposable.add(
            repository.getAllNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<NewsItem>>() {
                    override fun onSuccess(t: List<NewsItem>) {
                        newsList.value = t
                        Log.d(TAG, "API verileri başarıyla çekildi. Çekilen veri sayısı: ${t.size}")
                        loading.value = false
                        error.value = "true"
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "API verileri çekilirken hata oluştu", e)
                        loading.value = false
                        error.value = e.toString()
                    }
                }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    companion object {
        private const val TAG = "NewsListViewModel"
    }
}