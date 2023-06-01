package kz.aues.photohosting.data.account.sources

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import kz.aues.photohosting.data.account.entities.AccountFirebaseEntity
import kz.aues.photohosting.data.account.entities.SignInDataEntity
import kz.aues.photohosting.data.account.entities.SignUpFirestoreEntity
import kz.aues.photohosting.data.account.exceptions.AccountRecordNotFoundDataException
import kz.aues.photohosting.data.contracts.FirebaseAccountContract
import kz.aues.photohosting.data.settings.SettingsDataSource
import kz.aues.photohosting.data.utils.ParseDataException
import kz.aues.photohosting.data.utils.getAccountRef
import kz.aues.photohosting.data.utils.notExists
import javax.inject.Inject

class FirebaseAccountDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val settingsSource: SettingsDataSource
) : AccountDataSource {

    override suspend fun signIn(signInData: SignInDataEntity): String {
        val result = firebaseAuth.signInWithEmailAndPassword(
            /* email = */ signInData.email,
            /* password = */ signInData.password
        ).await()

        return result.user!!.uid
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun create(signUpFirestore: SignUpFirestoreEntity): String {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(
            /* email = */ signUpFirestore.email,
            /* password = */ signUpFirestore.password
        ).await()
        val userUid = authResult.user?.uid!!

        val dataAccount = hashMapOf(
            FirebaseAccountContract.NAME to signUpFirestore.name,
            FirebaseAccountContract.EMAIL to signUpFirestore.email,
            FirebaseAccountContract.ID to userUid,
            FirebaseAccountContract.AVATAR_URI to signUpFirestore.avatarUri
        )

        val newAccount = firestore.getAccountRef(userUid)
        newAccount.set(dataAccount).await()

        return userUid
    }

    override suspend fun read(accountId: String): AccountFirebaseEntity {
        val document = firestore.getAccountRef(accountId).get().await()

        if (document.notExists()) throw AccountRecordNotFoundDataException()
        return document.toObject<AccountFirebaseEntity>() ?: throw ParseDataException()
    }

    override suspend fun addImage(imageId: String) {
        val uid = settingsSource.getUid()
        val dataUpdate = mapOf(FirebaseAccountContract.IMAGES_IDS to FieldValue.arrayUnion(imageId))
        firestore.getAccountRef(uid).update(dataUpdate).await()
    }

    override suspend fun update(accountId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(accountId: String) {
        TODO("Not yet implemented")
    }

}