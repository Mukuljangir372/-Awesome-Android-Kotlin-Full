package com.mu.jan.problems.component

import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.processNextEventInCurrentThread

/**
 * ContentProvider is a application component in android
 * It allow you to supplies data from one to another application
 *
 * ContentResolver - It allow to query data
 *
 * Here is a example of ContentProvider, contacts app
 */
class Contact(var name: String? = null,var phoneNum: String? = null){}
class ContactActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        ContactHelper().loadContactList(contentResolver)

    }
}
class ContactHelper{
    private var list = mutableListOf<Contact>()
    fun loadContactList(contentResolver: ContentResolver){
        //query contacts
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
        null,null,null,null,null)
        if(cursor?.count!!>0){
            while(cursor.moveToNext()){
                //contact id
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                //query phoneNum from contact id
                //check, Is contact has phoneNum
                if(cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))>0){
                    val pCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            ,arrayOf(id),null)
                    if(pCursor?.count!!>0){
                        while(pCursor.moveToNext()){
                            val phoneNum = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            val name = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                            //remove spaces between phone number
                            list.add(Contact(name,phoneNum.replace("\\s".toRegex(),"")))
                        }
                        pCursor.close()
                    }
                }
            }
            cursor.close()
        }
    }
}































