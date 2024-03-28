package com.example.taskfour.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskfour.model.NewsItem
import com.example.taskfour.service.NewsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NewsListViewModel : ViewModel() {
    private val disposable = CompositeDisposable()
    private val apiService = NewsApiService()
    val newsList = MutableLiveData<List<NewsItem>>()

    private val loading = MutableLiveData<Boolean>()
    val loadingObs: LiveData<Boolean>
        get() = loading
    private val error = MutableLiveData<Boolean>()
    val errorObs: LiveData<Boolean>
        get() = error


    fun getDataFromAPi() {
        disposable.add(
            apiService.getNewsData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<NewsItem>>() {
                    override fun onSuccess(t: List<NewsItem>) {
                        newsList.value = t
                        Log.d(TAG, "API verileri başarıyla çekildi. Çekilen veri sayısı: ${t.size}")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "API verileri çekilirken hata oluştu", e)

                    }
                }
                ))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    companion object {
        private const val TAG = "NewsListViewModel"
    }
}