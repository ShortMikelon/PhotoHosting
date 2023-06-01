package kz.aues.photohosting.data.image.sources

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.image.exceptions.FailedCreateImageDataException
import kz.aues.photohosting.data.image.models.ImageUploadData
import java.util.*
import javax.inject.Inject

class FirebaseStorageImageDataSource @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : ImageDataSource {

    override suspend fun getUri(firebaseUri: String): Uri {
        val reference = firebaseStorage.reference.child(firebaseUri)
        return reference.downloadUrl.await()
    }

    override suspend fun upload(uploadData: ImageUploadData): String {
        val uuid = UUID.randomUUID().toString()
        val newImageRef = firebaseStorage.reference.child(BASE_URL + uuid)
        try {
            newImageRef.putBytes(uploadData.bytes).await()
            val uri = newImageRef.downloadUrl.await()
            return uri.toString()
        } catch (ex: StorageException) {
            throw FailedCreateImageDataException()
        }
    }

    companion object {
        private const val MAX_SIZE = 1024 * 1024L
        const val BASE_URL = "images/"
    }
}