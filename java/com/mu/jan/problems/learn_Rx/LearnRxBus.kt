package com.mu.jan.problems.learn_Rx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jakewharton.rxrelay3.PublishRelay
import com.mu.jan.cleanarchitecture_with_flow.R
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * RxBus is a part of RxRelay.
 * Here we push event from Activity B to Activity A
 *
 * @term Disposables - Disposables are short lived made to be dispose(clear) after use
 * @term by lazy - In kotlin, it allows to initialize variable only when we use it.
 *
 */
val RxBus by lazy { PublishRelay.create<User>() }

class User() {

}
class ActivityA : AppCompatActivity() {

    private val disposables by lazy { CompositeDisposable() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_rx_bus)
        //receive event from Activity B
        disposables.add(RxBus.subscribe {
            //you will receive data here
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
class ActivityB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_rx_bus)
        //push event to RxBus subscriber
        RxBus.accept(User())
    }
}
