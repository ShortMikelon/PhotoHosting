package kz.samsungcampus.android

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.jpgToBytes(): ByteArray {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    return baos.toByteArray()
}

fun Bitmap.pngToBytes(): ByteArray {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    return baos.toByteArray()
}