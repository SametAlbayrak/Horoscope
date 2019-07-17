package com.steelsoftware.horoscope.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steelsoftware.horoscope.data.NewsRepository
import com.steelsoftware.horoscope.data.firebase.FirestoreTaskRepository
import com.steelsoftware.horoscope.data.firebase.ITaskRepository
import com.steelsoftware.horoscope.data.firebase.Task
import com.steelsoftware.horoscope.utilities.extensions.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(var newsRepository: NewsRepository) : ViewModel() {
    // TODO: Implement the ViewModel
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
