package com.mu.jan.problems.ipc.aidl.server

import android.app.Service
import android.content.Intent
import android.os.IBinder


class MyBoundService: Service() {
    //aidl interface
    /**
     * here, MyAidlInterfaceServer java class
     * implements Stub class that extend Binder class
     */
    private val myBinder = object: MyAidlInterfaceServer.Stub(){
        override fun sum(n1: Int, n2: Int): Int {
            return n1 + n2
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return myBinder.asBinder()
    }

}