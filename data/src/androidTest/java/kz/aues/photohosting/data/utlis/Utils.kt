package kz.aues.photohosting.data.utlis

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Query.asFlow() = callbackFlow {
    val callback = addSnapshotListener { query, ex ->
        if (ex != null) {
            close(ex)
            return@addSnapshotListener
        }
        if (query != null)
            trySend(query)
    }

    awaitClose { callback.remove() }
}

fun DocumentReference.asFlow() = callbackFlow {
    val callback = addSnapshotListener { query, ex ->
        if (ex != null) {
            close(ex)
            return@addSnapshotListener
        }
        if (query != null)
            trySend(query)
    }

    awaitClose { callback.remove() }
}