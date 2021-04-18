package com.mu.jan.problems.threading

/**
 * Threading or thread - It's a unit of execution. When an application
 * launch, it create a thread named "main thread" which is responsible
 * for UI work.
 *
 * Processes - In android, all application components inside an application
 * run on same process and thread. If an application components starts, and there
 * is already exists a process for that application, then the component is started
 * within that same process.
 *
 * It's possible to start application components on multiple processes in same
 * application,
 * In manifest file, add 'android:process=":remote"' in application component
 * attribute.
 *
 * Work with Threading,
 * In android, Use kotlin coroutines, WorkManager
 */
