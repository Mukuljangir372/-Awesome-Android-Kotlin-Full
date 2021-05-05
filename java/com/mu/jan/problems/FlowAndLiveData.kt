package com.mu.jan.problems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * Flow -
 * Flow is a stream of data which emits values.
 *
 * LiveData-
 * In simple words, we can watch over the data if we use LiveData.
 * We set observer to LiveData, observer will trigger when values inside
 * liveData changes.
 *
 * setValue() and postValue() in LiveData
 * setValue() - it set the value from main thread immediately.
 * postValue() - it set the value from any thread. If you call postValue(),
 * and immediately call getValue(), chances are you will not get that value.
 * Because postValue() set data asynchronously.
 */

class MyFlow{
    fun a(){
        //flow
        var mFlow: Flow<Int> = flow {
            emit(1)
            emit(2)
            emit(3)
            emit(4)
        }
        //for collect these value
        //NOTE - mFlow.collect {} must be in any coroutine
        CoroutineScope(Dispatchers.Main).launch {
            mFlow.collect {

            }
        }


    }
}
data class MyDog(var name: String){}
class MyLiveData{
    fun a(){
        var mLiveData = MutableLiveData<MyDog>()
        /**
         * changing values
         */
//        mLiveData.value = ...
//        mLiveData.postValue = ...
          /**
           * watch live data changes
           *
           */
//        mLiveData.observe(this,{
//
//        })
    }
}


















