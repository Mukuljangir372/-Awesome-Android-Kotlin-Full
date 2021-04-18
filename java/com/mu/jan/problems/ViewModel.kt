package com.mu.jan.cleanarchitecture_with_flow.learn_mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 *  Mvvm stands for `ModelView` and `ViewModel`
 * `view` - It attached to UI code. It send user action to `viewModel` and don't act on it directly.
 * `model` - It's a data or business login. It consists repository, local or remote data source.
 * `viewModel` - It's a bridge between `view` and `model`. It act like a container for sharing data.
 *
 *
 * Advantages of using MVVM or viewModel
 * 1. It handles config changes of activity
 * 2. Prevent memory leaks
 * 3. viewModel handle lifecycle of activity.
 *
 * ViewModel only cleared when activity finish. Not cleared when activity create,start,resume,
 * pause,stop,destroyed
 *
 * viewModelScope is a part of kotlin coroutines, all tasks inside viewModelScope will
 * cancelled when viewModel cleared.
 * When activity finished, viewModel attached to activity will cleared
 */
class ActivityA : AppCompatActivity(){
    private var vm : ActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel
        vm = ViewModelProvider(this).get(ActivityViewModel::class.java)

        vm?.getResults()?.observe(this, Observer<List<String>> {
            //update ui
        })

    }

    //prevent memory leaks
    override fun onDestroy() {
        vm = null
        super.onDestroy()
    }
}
class ActivityViewModel : ViewModel() {
    private lateinit var mLiveData : MutableLiveData<List<String>>
    init {
        viewModelScope.launch {
            fetchResults()
        }
    }
    private fun fetchResults(){
        val result = ActivityRepo.getUserList()
        //..check for error
        mLiveData.value = result

    }
    fun getResults() = mLiveData
}
object ActivityRepo {
   fun getUserList(): List<String> {
       //fetch from local or remote data source or anything you want
       return listOf("1","2","3")
   }
}
