package com.steelsoftware.horoscope.ui.main.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.util.Log
import com.steelsoftware.horoscope.data.NewsRepository
import com.steelsoftware.horoscope.model.Articles
import com.steelsoftware.horoscope.model.NewsResult
import com.steelsoftware.horoscope.utilities.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by ansh on 09/03/18.
 */
class CategoryViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {

    val isLoading = ObservableField(false)

    var category: String? = null

    var news = MutableLiveData<List<Articles>>()

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()


    fun setNewsCategory(category: String) {
        this.category = category
        loadCategoryNews(category)
    }

    fun getNewsCategory(): String? {
        return this.category
    }

    private fun loadCategoryNews(category: String) {
        isLoading.set(true)

        compositeDisposable += newsRepository
                .getHeadlinesByCategory(category)
                .subscribeOn(Schedulers.io())   // Background thread
                .observeOn(AndroidSchedulers.mainThread()) // Android work on ui thread
                .subscribeWith(object : DisposableObserver<NewsResult>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                        Log.d("CategoryViewModel", "Erorr: " + e.message)
                    }

                    // called every time observable emits the data
                    override fun onNext(data: NewsResult) {
                        Log.d("CategoryViewModel", "in on next()")
                        news.value = data.articles
                    }

                    // called when observable finishes emitting all the data
                    override fun onComplete() {
                        isLoading.set(false)
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