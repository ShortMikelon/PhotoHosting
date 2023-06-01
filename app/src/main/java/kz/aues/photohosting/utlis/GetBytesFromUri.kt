package kz.aues.photohosting.utlis

import android.content.Context
import android.net.Uri
import kz.aues.photohosting.domain.main.sign_up.exceptions.FailedSelectImageException
import java.io.IOException

fun getBytesFromUri(context: Context, uri: String): ByteArray {
    try {
        val inputStream = context.contentResolver.openInputStream(Uri.parse(uri))
            ?: throw FailedSelectImageException()
        val bytes = inputStream.readBytes()
        inputStream.close()
        return bytes
    } catch (e: IOException) {
        throw FailedSelectImageException()
    }
}