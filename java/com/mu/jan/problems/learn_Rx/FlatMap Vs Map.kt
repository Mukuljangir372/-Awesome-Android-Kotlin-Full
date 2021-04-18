package com.mu.jan.problems.learn_Rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class Toy(var name: String? = null,var toyDetail: ToyDetail? = null){
}
class ToyDetail(var engine: String? = null){

}
//FlatMap vs Map operators in RxAndroid
class FlatMapVsMap{
    //observable
    private val observable = Observable.create<Toy> {
        if(!it.isDisposed){
            it.onNext(Toy("car"))
        }
        if(!it.isDisposed){
            it.onComplete()
        }
    }

    //map operator
    private fun map(){
        observable
                .map {
                    //filter or transform
                    it?.name = "Transformed Car"
                    //return
                    it
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<Toy>{
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: Toy?) {
                    }
                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }

                })
    }

    //flatMap operator
    private fun flatMap(){
        observable.flatMap {
            getToyDetailObservable(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<Toy>{
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: Toy?) {
                    }
                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }
                })

    }
    private fun getToyDetailObservable(toy: Toy): Observable<Toy>{
        return Observable.create<Toy> {
            if(!it.isDisposed){
                toy.toyDetail = ToyDetail("H12")
            }
            if(!it.isDisposed){
                it.onComplete()
            }
        }
    }
}