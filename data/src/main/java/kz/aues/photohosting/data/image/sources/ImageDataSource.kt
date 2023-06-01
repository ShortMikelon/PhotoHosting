package kz.aues.photohosting.data.image.sources

import android.net.Uri
import kz.aues.photohosting.data.image.models.ImageUploadData

interface ImageDataSource {

    suspend fun getUri(firebaseUri: String): Uri

    suspend fun upload(uploadData: ImageUploadData): String

}
