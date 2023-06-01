package kz.aues.photohosting.data.account.sources

import kz.aues.photohosting.data.account.entities.AccountFirebaseEntity
import kz.aues.photohosting.data.account.entities.SignInDataEntity
import kz.aues.photohosting.data.account.entities.SignUpFirestoreEntity

interface AccountDataSource {

    suspend fun signIn(signInData: SignInDataEntity): String

    fun signOut()

    suspend fun create(signUpFirestore: SignUpFirestoreEntity): String

    suspend fun read(accountId: String): AccountFirebaseEntity

    suspend fun update(accountId: String)

    suspend fun delete(accountId: String)

    suspend fun addImage(imageId: String)

}

