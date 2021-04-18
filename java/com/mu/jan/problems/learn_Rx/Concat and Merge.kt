package com.mu.jan.problems.learn_Rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

//RxAndroid
//For Parallel calls, use Zip operator
//For One after one api calls, use Concat
//Merge - Combines multiple observables emit values

/**
 * Concat function
 */
class Concat{
    private val observable1 = Observable.fromCallable { listOf("1","2","3") }
    private val observable2 = Observable.fromCallable { listOf("A","B","C") }

    private fun setup(){
        Observable.concat(observable1,observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<List<String>>{
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: List<String>?) {

                    }
                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }
                })

        //result - 123ABC

    }
}
class Merge{
    private val observable1 = Observable.fromCallable { listOf("1","2","3") }
    private val observable2 = Observable.fromCallable { listOf("A","B","C") }

    private fun setup(){
        Observable.merge(observable1,observable2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<List<String>>{
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: List<String>?) {

                    }
                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }
                })

        //result - 1A2B3C OR 12AB3C Or any combined order

    }
}