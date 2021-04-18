package com.mu.jan.problems

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Using custom listener has become a common thing among many developers.
 * Making custom listener in application is one of my fav things as a android
 * app developer.
 *
 * It's very easy to make custom listener. But easy problem is,
 * register or unregister listener with activity onStart() and onDestroy().
 * Maybe it might leak memory if listener not well unregistered.
 *
 * If custom listener tied to activity lifecycle, it can get better to use.
 *
 */
class MyCustomListenerActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        val observerListener = MyListener(this,object : ItemEvent{
            override fun onDataLoaded() {
            }
        })
        lifecycle.addObserver(observerListener)
    }
}
interface ItemEvent{
    fun onDataLoaded()
}
class MyListener(
    private var mContext: Context? = null,
    private var itemEvent: ItemEvent? = null
): LifecycleObserver{

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun connectListener(){

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectListener(){
        itemEvent = null
        mContext = null
    }
    fun dataLoaded(){
        itemEvent?.onDataLoaded()
    }
}
