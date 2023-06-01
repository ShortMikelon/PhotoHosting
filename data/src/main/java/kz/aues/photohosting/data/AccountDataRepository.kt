package kz.aues.photohosting.data

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.data.account.entities.AccountDataEntity
import kz.aues.photohosting.data.account.entities.AccountIdAndNameDataEntity
import kz.aues.photohosting.data.account.entities.SignInDataEntity
import kz.aues.photohosting.data.account.entities.SignUpDataEntity
import kz.samsungcampus.common.Resource

interface AccountDataRepository {

    suspend fun isSignIn(): Boolean

    suspend fun signIn(signInData: SignInDataEntity)

    suspend fun signOut()

    suspend fun addImage(imageId: String)

    suspend fun signUp(signUpDataEntity: SignUpDataEntity)

    fun getAccountById(accountId: String): Flow<Resource<AccountDataEntity>>

    fun update()

    fun delete()

    fun reload()

    suspend fun getCurrentAccountIdAndName(): AccountIdAndNameDataEntity
}