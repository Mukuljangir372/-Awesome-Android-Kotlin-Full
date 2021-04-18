package com.mu.jan.problems.ipc.aidl

/**
 * AIDL - Android Interface definition language
 * It provides a interface that both server and client communicates using IPC
 *
 * Step 1. Create same Aidl file with same method in both server and client project
 * Step 2. In server side - Create a Bound service and register it in Manifest file
 *
 * In manifest,
 *
        <service android:name=".ipc.aidl.server.MyBoundService"
            android:process=":remote"
            android:exported="true"
            android:enabled="true">
            <intent-filter>
                    <action android:name="AidlIntentAction"/>
                    <category android:name="android.intent.category.DEFAULT"/>
            </intent-filter>
        </service>
 *
 *
 * Step 3. In client side - In Activity, start bound service with intent
 *
 *
 * ISSUE -
 * 1. Aidl interface import issue - To solve it, Clean and Rebuild project
 *
 *
 */
