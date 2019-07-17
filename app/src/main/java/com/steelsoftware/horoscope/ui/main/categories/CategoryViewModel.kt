package com.steelsoftware.horoscope.ui.main.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import android.util.Log
import androidx.lifecycle.LiveData
import com.steelsoftware.horoscope.data.NewsRepository
import com.steelsoftware.horoscope.data.firebase.FirestoreTaskRepository
import com.steelsoftware.horoscope.data.firebase.ITaskRepository
import com.steelsoftware.horoscope.data.firebase.Task
import com.steelsoftware.horoscope.model.Articles
import com.steelsoftware.horoscope.model.NewsResult
import com.steelsoftware.horoscope.utilities.extensions.addTo
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

    // CompositeDisposable, a disposable container that can hold onto multiple other disposables
    private val compositeDisposable = CompositeDisposable()


    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>>
        get() = _taskList

    private val repository: ITaskRepository = FirestoreTaskRepository()

    private val disposable = CompositeDisposable()

    init {
        repository.getChangeObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            _taskList.value = it
                        },
                        {
                            it.printStackTrace()
                        }
                )
                .addTo(disposable)
    }

    fun deleteTask(taskId: String) {
        repository.deleteTask(taskId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {},
                        {
                            it.printStackTrace()
                        })
                .addTo(disposable)
    }

    fun addTask(taskTitle: String) {
        repository.addTask(Task("${System.currentTimeMillis()}", taskTitle))
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {},
                        {
                            it.printStackTrace()
                        })
                .addTo(disposable)
    }


    // In ViewModel's onCleared() method we should dispose all our disposables in CompositeDisposable
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}