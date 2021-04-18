package com.mu.jan.problems.architecture

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Now SharedPreferences has been replaced by DataStore.
 * SharedPreferences or DataStore, Basically, Used for storing
 * application key pair value.
 *
 */
val Context.dataStore : DataStore<Preferences> by preferencesDataStore("settings")
class LearnDataStore: AppCompatActivity(){
    companion object{
        val IS_DARK_MODE = booleanPreferencesKey("isDarkMode")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        CoroutineScope(Dispatchers.IO).launch {
            //write value
            writeValue(this@LearnDataStore)

            //read value
            readValue(this@LearnDataStore).collect {
                //value = it

            }
        }


    }
    private suspend fun writeValue(mContext: Context){
        mContext.dataStore.edit {
            it[IS_DARK_MODE] = true
        }
    }
    private suspend fun readValue(mContext: Context) : Flow<Boolean> =
        mContext.dataStore.data.map {
            it[IS_DARK_MODE] ?: false
        }
}