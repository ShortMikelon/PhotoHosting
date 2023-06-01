package kz.aues.photohosting.data.utils

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kz.aues.photohosting.data.contracts.FirebaseAccountContract
import kz.aues.photohosting.data.contracts.FirebaseCommentsContract
import kz.aues.photohosting.data.contracts.FirebaseImageContract

fun FirebaseFirestore.getAccountRef(id: String): DocumentReference =
    this.collection(FirebaseAccountContract.COLLECTION_NAME).document(id)

fun FirebaseFirestore.getNewAccountRef(): DocumentReference =
    this.collection(FirebaseAccountContract.COLLECTION_NAME).document()

fun FirebaseFirestore.getCommentRef(id: String): DocumentReference =
    this.collection(FirebaseCommentsContract.COLLECTION_NAME).document(id)

fun FirebaseFirestore.getNewCommentRef(): DocumentReference =
    this.collection(FirebaseCommentsContract.COLLECTION_NAME).document()

fun FirebaseFirestore.getImageRef(id: String): DocumentReference =
    this.collection(FirebaseImageContract.IMAGES_COLLECTION_NAME).document(id)

fun FirebaseFirestore.getNewImageRef(): DocumentReference =
    this.collection(FirebaseImageContract.IMAGES_COLLECTION_NAME).document()

fun FirebaseFirestore.getPreviewRef(id: String): DocumentReference =
    this.collection(FirebaseImageContract.PREVIEWS_COLLECTION_NAME).document(id)


fun DocumentSnapshot.notExists(): Boolean = !this.exists()