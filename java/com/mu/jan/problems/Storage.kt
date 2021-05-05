package com.mu.jan.problems

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI

/**
 * Here, we pick a image from gallery, and save it to storage.
 */
class StorageActivity: AppCompatActivity(){

    private val imagePickRequest = 1
    private val readExternalPermission = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //...
        //check permission for read storage
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            //permission not granted
            //ask for permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),readExternalPermission)
        }else {
           startIntentForImagePick()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            imagePickRequest -> {
                if(data!=null){
                    val uri = data.data
                    if(uri!=null){
                        //get bitmap from uri
                        var bitmap : Bitmap = if(Build.VERSION.SDK_INT>=29){
                            val source = ImageDecoder.createSource(contentResolver,uri)
                            ImageDecoder.decodeBitmap(source)
                        }else {
                            MediaStore.Images.Media.getBitmap(contentResolver,uri)
                        }
                        //save
                        saveImageToStorage(this,bitmap)
                    }

                }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            readExternalPermission -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startIntentForImagePick()
                }
            }
        }
    }

    private fun startIntentForImagePick(){
        //pick a image from gallery
        val i = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        startActivityForResult(i,imagePickRequest)
    }
    private fun saveImageToStorage(mContext: Context,bitmap: Bitmap){
        //destinationDir
        val destinationDir =
            File("${mContext.filesDir}${File.separator}AppName${File.separator}Images")
        //create if not exists
        if(!destinationDir.exists()) destinationDir.mkdirs()
        //image jpg file
        val file = File(destinationDir,"image_name.jpg")

        if(!file.exists()){
            var fileOutputStream : FileOutputStream? = null
            try{
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

            }catch (e: Exception){}
        }
        //path
        //return file.path
    }
    private fun readImageFromStorage(mContext: Context){
        //destinationDir
        val destinationDir =
            File("${mContext.filesDir}${File.separator}AppName${File.separator}Images")
        if(destinationDir.exists()) {
            //image jpg file
            val file = File(destinationDir,"image_name.jpg")
            if(file.exists()){
                //get bitmap
                val bitmap = BitmapFactory.decodeStream(FileInputStream(file))

                //imageView.setImageBitmap(bitmap)
            }
        }


    }
}
























