package com.mu.jan.problems.ipc.aidl.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyClientActivity: AppCompatActivity() {
    private var myAidlInterfaceClient: MyAidlInterfaceClient? = null

    private var myConnection = object: ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            showToast("connected")
            myAidlInterfaceClient = MyAidlInterfaceClient.Stub.asInterface(service)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            showToast("disconnected")
            myAidlInterfaceClient = null
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
        //onButtonClick
        showResult()
    }
    //result
    private fun showResult(){
        val n1 = 1
        val n2 = 2
        var result = myAidlInterfaceClient?.sum(n1,n2)
        showToast(result.toString())
    }
    //toast
    private fun showToast(s: String){
        Toast.makeText(this,s,Toast.LENGTH_LONG).show()
    }

    private fun startConnection(){
        if(myAidlInterfaceClient==null){
            val intent = Intent("AidlIntentAction").apply {
                setClassName("com.mu.jan.problems.ipc.aidl.server",
                    "com.mu.jan.problems.ipc.aidl.server.MyBoundService")
            }
            val isBound = applicationContext.bindService(intent,myConnection,Context.BIND_AUTO_CREATE)
            if(isBound) {
                showToast("Bounded")
            }else {
                showToast("Not Bounded")
            }
        }

    }
    private fun stopConnection(){
        applicationContext.unbindService(myConnection)
        myAidlInterfaceClient = null
    }
    override fun onStart() {
        super.onStart()
        startConnection()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopConnection()
    }
}