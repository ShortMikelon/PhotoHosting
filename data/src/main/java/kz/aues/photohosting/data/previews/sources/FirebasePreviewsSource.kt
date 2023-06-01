package kz.aues.photohosting.data.previews.sources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.contracts.FirebaseImageContract
import kz.aues.photohosting.data.image.exceptions.ImageNotFoundDataException
import kz.aues.photohosting.data.previews.entities.PreviewDescriptionFirebaseData
import kz.aues.photohosting.data.utils.ParseDataException
import javax.inject.Inject

class FirebasePreviewsSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : PreviewsDataSource {

    override suspend fun getPreviews(): List<PreviewDescriptionFirebaseData> {
        val documents = firestore
            .collection("previews")
            .orderBy(FirebaseImageContract.CREATED_AT)
            .limit(50)
            .get()
            .await()
            .documents

        if (documents.isEmpty()) return emptyList()
        return coroutineScope {
            val previews = documents.map {
                async {
                    if (it == null) throw ImageNotFoundDataException()
                    it.toObject<PreviewDescriptionFirebaseData>() ?: throw ParseDataException()
                }
            }
            return@coroutineScope previews.awaitAll()
        }
    }

    override suspend fun getPreviewsByAuthorId(authorId: String): List<PreviewDescriptionFirebaseData> {
        val snapshot = firestore.collection(FirebaseImageContract.IMAGES_COLLECTION_NAME)
            .whereEqualTo(FirebaseImageContract.AUTHOR_ID, authorId).get().await()

        if (snapshot.isEmpty) return emptyList()
        val previews = mutableListOf<PreviewDescriptionFirebaseData>()
        for (document in snapshot.documents) {
            val preview =
                document.toObject<PreviewDescriptionFirebaseData>() ?: throw ParseDataException()
            preview.id = document.id
            previews.add(preview)
        }
        return previews
    }
}