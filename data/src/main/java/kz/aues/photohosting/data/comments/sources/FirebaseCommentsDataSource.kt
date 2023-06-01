package kz.aues.photohosting.data.comments.sources

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.comments.entities.CommentFirebaseEntity
import kz.aues.photohosting.data.comments.entities.SendCommentData
import kz.aues.photohosting.data.contracts.FirebaseCommentsContract
import kz.aues.photohosting.data.contracts.FirebaseImageContract
import javax.inject.Inject

class FirebaseCommentsDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : CommentsDataSource {

    override suspend fun getComments(imageId: String): List<CommentFirebaseEntity> =
        coroutineScope {
            val collectionRef = firestore.collection(FirebaseCommentsContract.COLLECTION_NAME)
            val documents =
                collectionRef.whereEqualTo(FirebaseCommentsContract.IMAGE_ID, imageId).get()
                    .await().documents
            val deferredResult = documents.map { async { it.toObject<CommentFirebaseEntity>() } }
            deferredResult.awaitAll().filterNotNull()

        }

    override suspend fun putComment(comment: SendCommentData) {
        val newCommentRef = firestore.collection(FirebaseCommentsContract.COLLECTION_NAME)
            .document()

        val dataUpdate = HashMap<String, Any>()
        dataUpdate[FirebaseCommentsContract.IMAGE_ID] = comment.imageId
        dataUpdate[FirebaseCommentsContract.AUTHOR] = comment.author
        dataUpdate[FirebaseCommentsContract.AUTHOR_ID] = comment.authorId
        dataUpdate[FirebaseCommentsContract.CREATED_AT] = comment.createdAt
        dataUpdate[FirebaseCommentsContract.TEXT] = comment.text
        dataUpdate[FirebaseCommentsContract.ID] = newCommentRef.id

        newCommentRef.set(dataUpdate).await()

        val imageDataUpdate = HashMap<String, Any>()
        imageDataUpdate[FirebaseImageContract.COUNT_COMMENTS] = FieldValue.increment(1)
        imageDataUpdate[FirebaseImageContract.COMMENTS] = FieldValue.arrayUnion(newCommentRef.id)
        firestore.collection(FirebaseImageContract.IMAGES_COLLECTION_NAME)
            .document(comment.imageId)
            .update(imageDataUpdate)
            .await()
    }

}