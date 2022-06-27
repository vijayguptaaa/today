package com.example.loginvalidation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

object Converters {


    fun imageToString(imageView: ImageView) : String{
        imageView.buildDrawingCache()
        val bitmap = imageView.drawingCache
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG,80,outputStream)
        val b2 =  outputStream.toByteArray()
        return Base64.encodeToString(b2,Base64.DEFAULT)
    }

    fun stringToBitmap(stringImage:String) :Bitmap{
        val data:ByteArray = Base64.decode(stringImage,Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(data,0, data.size)
    }
}