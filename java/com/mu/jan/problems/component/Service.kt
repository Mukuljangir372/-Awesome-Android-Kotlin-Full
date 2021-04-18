package com.mu.jan.problems.applicationcomponent

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

/**
 * Service is a application component which helps in background
 * or foreground asynchronous tasks. Service runs in Main thread.
 *
 * There are three types of Service in android
 * 1. Background - Used for Background tasks
 * 2. Foreground - Used for foreground tasks that user aware with it
 * 3. Bound - Bound service provides a way to request or response results
 *            between client and service using IPC (inter process communication)
 *
 * Bound service used in IPC.
 * IPC - IPC provides a mechanism to communicate with android application using
 * intents, Binders, Bundles etc.
 *
 * START_STICKY
 * the system will try to re-create your service after it is killed
 *
 * START_NOT_STICKY
 * the system will not try to re-create your service after it is killed
 *
 */
class ServiceActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
        /**
         * background service
         */
        startService(Intent(this,BackgroundService::class.java))

        /**
         * Foreground service
         */
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            startForegroundService(Intent(this,ForegroundService::class.java))
        }else {
            startService(Intent(this,ForegroundService::class.java))
        }
    }
}
class BackgroundService: Service(){

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
class ForegroundService: Service(){
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    override fun onCreate() {
        super.onCreate()
//        startForeground(1,mNotification)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

/**
 * Bound service
 */
class BoundServiceActivity: AppCompatActivity(){
    private var isBound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
    }
    private var mConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }
    override fun onStart() {
        super.onStart()
        bindService(Intent(this,BoundService::class.java),mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
        isBound = false
    }
}
class BoundService: Service(){
    private val localBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return localBinder
    }
    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
    inner class LocalBinder: Binder(){
        fun getService() = this@BoundService
    }
}

