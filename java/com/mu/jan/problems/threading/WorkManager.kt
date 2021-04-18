package com.mu.jan.problems.component

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * Now, IntentService has been deprecated
 * Use WorkManger or jobIntentService
 *
 * WorkManager - Used for scheduling asynchronous task that expected to run
 * even if app not running.
 *
 * To use WorkManager, we need,
 * 1. YourWorker class that extends Worker class.
 * 2. WorkRequest - it define how and when task or work will run based on constraints.
 *                - it can be OneTimeWorkRequest or PeriodicWorkTimeRequest.
 *
 * 3. At the end, Schedule task with WorkManager.
 */
class UploadWorker(appContext: Context, workerParameters: WorkerParameters)
    : Worker(appContext,workerParameters){
    override fun doWork(): Result {
     return Result.success()
    }
}
class Basic{
    //work request -
    private var myUploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>().build()

    private fun requestWork(mContext: Context){
        //This is How you schedule work with WorkManger
        WorkManager.getInstance(mContext).enqueue(myUploadWorkRequest)
    }

}
class Full{
    /**
     * Work request constraints,
     * for instance,
     * .setRequireCharging(true) - it means, work request will
     * enqueue only if device will charging.
     * .setRequireNetworkType(NetworkType.CONNECTED) - it means, work request will
     * enqueue only if network connected.
     * ...
     */
    private var myConstraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()

    /**
     * Here, OneTimeWorkRequestBuilder will do work once
     */
    private var myUploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setConstraints(myConstraints)
            .build()
    /**
     * Here, PeriodicWorkRequestBuilder will repeat
     * same work every 1 hour.
     */
    private var myUploadWorkRequest1 = PeriodicWorkRequestBuilder<UploadWorker>(1,TimeUnit.HOURS)
            .setConstraints(myConstraints)
            .build()

    /**
     * Request work
     */
    private fun requestWork(mContext: Context){
        WorkManager.getInstance(mContext).enqueue(myUploadWorkRequest)
    }
    /**
     * Get info about requested work
     */
    private fun getInfoRequestedWork(mContext: Context){

        //Don't use like this, Use you activity lifeCycleOwner
        var lifeCycleOwner : LifecycleOwner? = null

        WorkManager.getInstance(mContext)
                .getWorkInfoByIdLiveData(myUploadWorkRequest.id)
                .observe(lifeCycleOwner!!, Observer {
                    //state
                    if(it.state == WorkInfo.State.SUCCEEDED){
                        //success
                    }
                })
    }
    /**
     * Chaining work with WorkManager
     * we can do multiple tasks with chaining work
     * 1. Multiple tasks in series
     * 2. Multiple tasks in parallel
     */
    private var workRequest1 = OneTimeWorkRequestBuilder<UploadWorker>()
            .build()
    private var workRequest2 = OneTimeWorkRequestBuilder<UploadWorker>().build()

    private fun requestMultipleWork(mContext: Context){
        //multiple works in series
        WorkManager.getInstance(mContext).beginWith(workRequest1)
                .then(workRequest2).enqueue()
    }
    private fun requestMultipleWork1(mContext: Context){
        //multiple works in parallel
        WorkManager.getInstance(mContext)
                .beginWith(listOf(workRequest1,workRequest2))
                .enqueue()
    }

    /**
     * cancel work
     */
    private fun cancelWork(mContext: Context){
        //for cancel all work
        WorkManager.getInstance(mContext).cancelAllWork()
        //for cancel work by id
        WorkManager.getInstance(mContext).cancelWorkById(workRequest1.id)
    }
    /**
     * input data - sending data to Worker class
     */
    var data = Data.Builder()
            .putString("key","value")
            .build()
    var myRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setInputData(data)
            .build()

    inner class MyWorker(appContext: Context,params: WorkerParameters)
        : Worker(appContext,params){
        override fun doWork(): Result {
            //get name
            var value = inputData.getString("key")

            //Or you can can output data
            var data = Data.Builder()
                    .putString("key","value")
                    .build()

            return Result.success(data)
        }
    }
    /**
     * Output data - receiving data from worker class
     */
    private fun receive(mContext: Context){
        //Don't use like this, Use you activity lifeCycleOwner
        var lifeCycleOwner : LifecycleOwner? = null

        WorkManager.getInstance(mContext)
                .getWorkInfoByIdLiveData(myRequest.id)
                .observe(lifeCycleOwner!!, Observer {
                    //state
                    if(it.state == WorkInfo.State.SUCCEEDED){
                        //success
                        //get Output data
                        val data = it.outputData

                    }
                })
    }


}

