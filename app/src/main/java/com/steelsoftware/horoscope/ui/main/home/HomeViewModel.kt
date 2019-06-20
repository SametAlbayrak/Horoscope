package com.steelsoftware.horoscope.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import android.util.Log
import com.steelsoftware.horoscope.api.Status
import com.steelsoftware.horoscope.data.NewsRepository
import com.steelsoftware.horoscope.model.Articles
import com.steelsoftware.horoscope.model.NewsResult
import com.steelsoftware.horoscope.ui.SingleLiveEvent
import com.steelsoftware.horoscope.utilities.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ansh on 13/02/18.
 */

// All of our view models should extend the ViewModel() class
class HomeViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    // ObservableField is a class from Data Binding library that we can use instead of
    // creating an Observable object. It wraps the object that we would like to be observed.
    val isLoading = ObservableField(false)

    val isRefreshing = ObservableBoolean(false)


    var news = MutableLiveData<List<Articles>>()

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()


    private val status = SingleLiveEvent<Status>()


    fun getStatus(): LiveData<Status> {
        return status
    }

    fun getNewsItemCount(): Int? {
        return news.value?.size
    }


    init {
        // to load news articles first time
        loadRepositories()
    }

    fun loadRepositories() {
        isLoading.set(true)

        // we can choose which thread will observable operate on using subscribeOn() method and
        // which thread observer will operate on using observeOn() method. Usually, all code
        // from data layer should be operated on background thread.
        compositeDisposable += newsRepository
                .getRepositories()
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableObserver<NewsResult>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                        Log.d("HomeVieWModel", "Erorr: " + e.message)
                        isLoading.set(false)

                        news.value = arrayListOf()

                        if(e.message!!.contains("Unable to resolve host")){
                            status.value = Status.NO_NETWORK
                        }

                        else{
                            status.value = Status.ERROR
                        }
                    }

                    // called every time observable emits the data
                    override fun onNext(data: NewsResult) {
                        Log.d("HomeViewModel", "in on next()")
                        status.value = Status.SUCCESS
                        news.value = data.articles
                    }

                    // called when observable finishes emitting all the data
                    override fun onComplete() {
                        isLoading.set(false)
                    }
                })
    }

    // for swipe to refresh
    fun onRefresh() {
        isRefreshing.set(true)

        // we can choose which thread will observable operate on using subscribeOn() method and
        // which thread observer will operate on using observeOn() method. Usually, all code
        // from data layer should be operated on background thread.
        compositeDisposable += newsRepository
                .getRepositories()
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableObserver<NewsResult>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                        isRefreshing.set(false)

                    }

                    // called every time observable emits the data
                    override fun onNext(data: NewsResult) {
                        Log.d("HomeViewModel", "in on next()")
                        status.value = Status.SUCCESS
                        news.value = data.articles
                    }

                    // called when observable finishes emitting all the data
                    override fun onComplete() {
                        isRefreshing.set(false)
                    }
                })
    }

    // In ViewModel's onCleared() method we should dispose all our disposables in CompositeDisposable
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}