package kz.aues.photohosting.data.utils

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.CancellationException
import kz.samsungcampus.common.AppException
import kz.samsungcampus.common.Resource

suspend fun <T> createResource(block: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(block())
    } catch (e: AppException) {
        Resource.Error(e)
    } catch (e: FirebaseAuthException) {
        handleFirebaseAuthException(e)
    } catch (e: FirebaseFirestoreException) {
        val appException = StorageDataException(StorageDataException.SERVER_EXCEPTION_MESSAGE)
        Resource.Error(appException)
    } catch (e: CancellationException) {
        throw e
    }
}

private fun handleFirebaseAuthException(e: FirebaseAuthException): Resource.Error {

    return Resource.Error(AppException())
}
