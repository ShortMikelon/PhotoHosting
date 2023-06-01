package kz.aues.photohosting.domain

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountIdAndNameEntity
import kz.samsungcampus.common.Resource

interface AccountRepository {

    suspend fun isSignIn(): Boolean

    suspend fun getCurrentAccountNameAndId(): AccountIdAndNameEntity

    suspend fun signIn(signIn: SignInEntity)

    suspend fun signOut()

    suspend fun signUp(signUpEntity: SignUpEntity)

    suspend fun getAccountById(accountId: String): Flow<Resource<AccountEntity>>

    fun update()

    fun delete()

    fun reload()

    suspend fun addImage(imageId: String)

}