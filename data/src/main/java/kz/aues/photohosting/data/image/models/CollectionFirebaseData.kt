package kz.aues.photohosting.data.image.models

import com.google.firebase.firestore.PropertyName
import kz.aues.photohosting.data.contracts.FirebaseCollectionContract

data class CollectionFirebaseData(
    @PropertyName(FirebaseCollectionContract.COLLECTION_ID)
    val id: String,
    @PropertyName(FirebaseCollectionContract.COLLECTION_NAME)
    val name: String,
    @PropertyName(FirebaseCollectionContract.COLLECTION_IMAGE_ID)
    val imagesId: List<String>
)

