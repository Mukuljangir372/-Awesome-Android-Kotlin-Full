package com.mu.jan.problems.architecture

import android.content.Context
import android.content.SharedPreferences

class SharePreferences {
    /**
     * Used for storing application key pair values
     */
    private val sharedPreferencesName = "com.mu.jan.problems.sharedPreferences"

    private fun getSharedPreference(mContext: Context) = mContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)

    private fun getValue(mContext: Context): String{
        return getSharedPreference(mContext).getString("key","defaultValue")!!
    }
    private fun saveValue(mContext: Context){
        //apply() - returns nothing, store data asynchronously
        getSharedPreference(mContext).edit().putString("key","value").apply()
        //OR
        //commit() - return true if value stored successfully otherwise false. It is not
        //asynchronous
        getSharedPreference(mContext).edit().putString("key","value").commit()
    }
}