package com.mu.jan.problems.applicationcomponent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Global vs Local BroadCast Receiver
 * Global - Scope inside whole system
 * Local - Scope inside only one application
 *
 * Note - Now LocalBroadcastReceiver has been removed
 * And sendStickyBroadcast() has been deprecated
 */
class Broadcast: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        //register (GlobalBroadCastReceiver)
        //also define receiver in manifest file
        val receiver = MyBroadCastReceiver()
        registerReceiver(receiver, IntentFilter(MyBroadCastAction.broadcastAction))

        //send broadcast (GlobalBroadCastReceiver)
        val intent = Intent(MyBroadCastAction.broadcastAction)
        //intent.putExtra...
        sendBroadcast(intent)

    }
}
class MyBroadCastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!=null){
            when(intent.action){
                MyBroadCastAction.broadcastAction-> {
                    //onReceive
                }
            }
        }
    }
}
object MyBroadCastAction{
    const val broadcastAction: String = "com.mu.jan.problems.MyAction"
}