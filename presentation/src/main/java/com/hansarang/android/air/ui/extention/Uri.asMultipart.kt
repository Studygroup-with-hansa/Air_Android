package com.hansarang.android.air.ui.extention

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import kotlin.IllegalArgumentException

fun Uri.asMultipart(name: String, cacheDir: File, contentResolver: ContentResolver): MultipartBody.Part? {
    return contentResolver.query(this, null, null, null, null)?.let {
        if (it.moveToNext()) {
            val displayName = try {
                it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            } catch (e: IllegalArgumentException) {
                it.close()
                return null
            }
            val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver,this))

            val storage: File = cacheDir
            val tempFile = File(storage, displayName)
            tempFile.createNewFile()
            val out = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            it.close()

            val imageFile = File("${cacheDir}/${displayName}")
            val requestBody = imageFile.asRequestBody(contentResolver.getType(this)?.toMediaType())

            MultipartBody.Part.createFormData(name, displayName, requestBody)
        } else {
            it.close()
            null
        }
    }
}