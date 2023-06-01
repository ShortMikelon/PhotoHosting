package kz.aues.photohosting.data.image.sources

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.image.exceptions.ImageNotFoundDataException
import kz.aues.photohosting.data.image.models.PhotoDescriptionUploadData
import kz.aues.photohosting.data.contracts.FirebaseImageContract
import kz.aues.photohosting.data.image.models.ImageFirebaseEntity
import kz.aues.photohosting.data.utils.ParseDataException
import kz.aues.photohosting.data.utils.getPreviewRef
import javax.inject.Inject

class FirestoreImageDescriptionDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : ImageDescriptionDataSource {

    override suspend fun getImageDescriptionById(id: String): ImageFirebaseEntity {
        val snapshot = getDocument(id).get().await()
        if (!snapshot.exists()) throw ImageNotFoundDataException()

        return snapshot.toObject<ImageFirebaseEntity>()
            ?: throw ParseDataException()
    }

    override suspend fun uploadData(photoDescription: PhotoDescriptionUploadData): String {
        val newImageRef = firestore
            .collection(FirebaseImageContract.IMAGES_COLLECTION_NAME).document()
        val dataMap = mapOf(
            FirebaseImageContract.ID to newImageRef.id,
            FirebaseImageContract.NAME to photoDescription.name,
            FirebaseImageContract.AUTHOR to photoDescription.author,
            FirebaseImageContract.AUTHOR_ID to photoDescription.authorId,
            FirebaseImageContract.DESCRIPTION to photoDescription.description,
            FirebaseImageContract.CREATED_AT to photoDescription.createdAt,
            FirebaseImageContract.IMAGE_PATH to photoDescription.path,
        )
        newImageRef.set(dataMap).await()
        val dataPreview = mapOf(
            FirebaseImageContract.ID to newImageRef.id,
            FirebaseImageContract.CREATED_AT to photoDescription.createdAt,
            FirebaseImageContract.IMAGE_PATH to photoDescription.path,
        )
        firestore.getPreviewRef(newImageRef.id).set(dataPreview).await()
        return newImageRef.id
    }

    private fun getDocument(id: String): DocumentReference {
        return firestore.collection(FirebaseImageContract.IMAGES_COLLECTION_NAME).document(id)
    }

}



