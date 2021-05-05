package com.mu.jan.problems.threading

import kotlinx.coroutines.*

/**
 * If you're working with background tasks, Use kotlin coroutines.
 * kotlin coroutines designed for asynchronous task.
 *
 * https://developer.android.com/
 *
 * GlobalScope - Used for creating a new coroutine in Globe scope
 * CoroutineScope - Used for creating a new coroutine in coroutine scope
 *
 * viewModelScope - Used for creating a new coroutine.
 * Use it inside viewModel. It prevent memory leaks.
 * It get's cleared when viewModel gets cleared. Because viewModel lifecycle tied to
 * viewModelScope.
 *
 * lifeCycleScope - Used for creating a new coroutine.
 * Use it inside activity or fragment.
 * It's lifecycle tied to activity or fragment lifecycle. When activity or fragment
 * finished or destroyed, all jobs insides lifeCycleScope gets cancelled.
 * It prevent memory leaks.
 *
 */
class KotlinCoroutines{
    fun a(){
        //launch a coroutine scope for main thread
        CoroutineScope(Dispatchers.Main).launch {

        }
        //launch a coroutine scope for IO(input output tasks)
        //tasks like updating value to firebase or any database, bitmap etc.
        CoroutineScope(Dispatchers.IO).launch {

        }
        //launch a coroutine scope for CPU intensive tasks
        CoroutineScope(Dispatchers.Default).launch {

        }
        //launch a coroutine scope for very small tasks
        //NOTE - Not recommended to use anywhere.
        CoroutineScope(Dispatchers.Unconfined).launch {

        }
    }
    /**
     * Parallel vs series tasks using Coroutines
     */
    fun b(){
        //parallel tasks
        CoroutineScope(Dispatchers.IO).launch {
            launch {  }
            launch {  }
        }
        //series tasks
        CoroutineScope(Dispatchers.IO).launch {
            //first task
            //...
            //second task
            //...
        }
    }
    /**
     * Coroutine launch vs async
     * launch - launch a coroutine inside a thread. But it doesn't return results.
     * async - launch a coroutine inside a thread. But it return results.
     *
     * await() - wait until coroutine finished with job done.
     */
    suspend fun fetchUsers() = CoroutineScope(Dispatchers.IO).async {

    }.await()
    /**
     * above fetchUsers() function code has been replaced by withContext() function.
     * withContext() does nothings, but it return value until coroutine finished like await()
     * function. In withContext(), no need to write await() function.
     * In simple words, withContext() do the same job as await does.
     */
    suspend fun fetchUsers1() =
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {

        }

}























