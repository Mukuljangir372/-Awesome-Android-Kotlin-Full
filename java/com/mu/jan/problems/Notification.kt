package com.mu.jan.problems.applicationcomponent

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mu.jan.problems.R

class Notification: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        NotificationHelper.push(this@Notification)
    }
}
object NotificationHelper{
    private const val CHANNEL_ID = "1"
    fun push(mContext: Context){
        //intent
        val intent = Intent().apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(mContext,0,intent,0)

        //channel
        createNotificationChannel(mContext)

        //notification
        val builder = NotificationCompat.Builder(mContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
//            .setLargeIcon()
            .setContentTitle("Title")
            .setContentText("This is my notification")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("This is my notification"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        //push
        with(NotificationManagerCompat.from(mContext)){
            notify(123,builder)
        }

    }
    private fun createNotificationChannel(mContext: Context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //create notification channel
            val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //channel
            val channel = NotificationChannel("id","channel_name",NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
    }
}