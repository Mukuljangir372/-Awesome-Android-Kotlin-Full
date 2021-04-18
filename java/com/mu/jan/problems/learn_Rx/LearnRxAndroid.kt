package com.mu.jan.problems.learn_Rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
//RxAndroid - For multithreading, asynchronous tasks etc.
//RxAndroid is extension of Rxjava. It's work is same as Rxjava.
//But one major different between Rxjava and RxAndroid, It introduced main thread
//that we use it for updating UI in our apps. For UI related work, it provides
//AndroidScheduler.mainThread() for working on UI thread.

class LearnRxAndroid {
    /**
     * Basic -
     * @terms Observables - emit value
     * @terms Observer - keep an eye on Observable
     * @terms subscribe - setup observer with observable
     */
    /**
     * Disposable - It is a link between observer and observable.
     * In simple terms, they're short lived made to be dispose(clear) after use.
     *
     * CompositeDisposable - There are many times you need more than one disposable.
     */

    //we have a list of items
    private fun getUserListFromClient() = listOf("a","b","c","d","e")
    //compositeDisposable
    private val compositeDisposable = CompositeDisposable()
    //basic
    inner class Basic(){
        //Observable.just() - it creates a new observable
        private val observable: Observable<List<String>> = Observable.just(getUserListFromClient())
        //setup a observer to watch observable
        fun setup(){
            observable.subscribe(object: Observer<List<*>> {
                override fun onComplete() {
                    //complete
                }
                override fun onNext(t: List<*>?) {
                    //emit a single item from list
                }
                override fun onError(e: Throwable?) {
                    //error
                }
                override fun onSubscribe(d: Disposable?) {
                    //when setup completed
                }
            })
        }
    }
    //Disposable
    inner class UseDisposable {
        /**
         * Doing asynchronous operation with RxAndroid.
         * In case, getting results from local or remote data source or any use you want.
         *
         * @methods Observable.fromCallable{} -
         * Using callable, it give two advantages
         * 1. The code which emit value will not run until someone subscribe to Observable.
         * 2. The code can be run on different threads.
         *
         * @methods observable.subscribeOn(Schedulers.io()) -
         * In this, we can tell to observable to subscribe on background thread
         * In simple terms, In which thread (main or background thread) we want to get our
         * results from our client.
         *
         * @methods observable.observeOn(AndroidSchedulers.mainThread()) -
         * In this, we can tell to observer to observe the observable on main thread
         * In simple terms, In which thread (main or background thread) we want to observe
         * our results.
         *
         * @methods observable.subscribe() -
         * It will start getting results from client.
         *
         */

        private var observable1 = Observable.fromCallable { getUserListFromClient() }
        //setup
        fun setup1(){
            val obs = observable1.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableObserver<List<*>>(){
                        override fun onComplete() {
                        }
                        override fun onNext(t: List<*>?) {
                        }
                        override fun onError(e: Throwable?) {
                        }
                    })

            compositeDisposable.add(obs)

            /**
             * In last, for preventing memory leaks,
             * it's very important that you unsubscribe or stop getting results from client
             * when you activity has been destroyed.
             *
             *  @Override
             *  protected void onDestroy() {
             *     super.onDestroy();
             *     disposables.clear(); // do not send event after activity has been destroyed
             *     disposables.dispose();
             *  }
             *
             *
             */
            //do it when activity destroyed
            compositeDisposable.clear() //don't send event after activity destroyed
            compositeDisposable.dispose()



        }
    }
    //Single operator
    inner class UseSingleObservableAndDisposable{
        /**
         * Using Observables are great, because emit values every time,
         * may be time consuming. To deal with it, use Single<> with RxAndroid.
         * Single<> returns only result without emit values.
         *
         */
        private var single = Single.fromCallable { getUserListFromClient() }

        fun setup(){
            val call = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableSingleObserver<List<*>>() {
                        override fun onSuccess(t: List<*>?) {

                        }

                        override fun onError(e: Throwable?) {
                        }

                    })
            compositeDisposable.add(call)

            //clear and dispose disposable when activity destroyed
            /**
             * compositeDisposable.clear()
             * compositeDisposable.dispose()
             */
        }


    }
    //Parallel calls using Zip operator
    inner class MultipleOperationsAtSameTime(){
        /**
         * Multiple Operation or multiple api calls at same time asynchronously
         */
        private val observable1 = Observable.fromCallable { getUserListFromClient() }
        private val observable2 = Observable.fromCallable { getUserListFromClient() }
        private val observable3 = Observable.fromCallable { getUserListFromClient() }

        fun setup(){
            val call = Observable.zip(
                    observable1,observable2,observable3,
                    Function3<List<*>, List<*>, List<*>, List<String>> { t1, t2, t3 ->
                        var resultOfApiCalls = mutableListOf<String>()
                        //resultOfApiCalls.add(t1_api_response_data)
                        //resultOfApiCalls.add(t2_api_response_data)
                        //resultOfApiCalls.add(t3_api_response_data)
                        resultOfApiCalls
                    }
            ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object: DisposableObserver<List<*>>(){
                        override fun onComplete() {
                        }
                        override fun onNext(t: List<*>?) {
                        }
                        override fun onError(e: Throwable?) {
                        }
                    })

            compositeDisposable.add(call)

            //clear and dispose disposable when activity destroyed
            /**
             * compositeDisposable.clear()
             * compositeDisposable.dispose()
             */


        }
    }


}