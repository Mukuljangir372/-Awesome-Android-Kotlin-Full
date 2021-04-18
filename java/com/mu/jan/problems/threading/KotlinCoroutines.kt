package com.mu.jan.problems.threading

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