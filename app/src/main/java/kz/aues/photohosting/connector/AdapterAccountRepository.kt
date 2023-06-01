package kz.aues.photohosting.connector

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.aues.photohosting.connector.mappers.AccountMapper
import kz.aues.photohosting.data.AccountDataRepository
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountEntity
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountIdAndNameEntity
import kz.samsungcampus.common.Resource
import kz.samsungcampus.common.Resource.Pending.map
import javax.inject.Inject

class AdapterAccountRepository @Inject constructor(
    private val accountDataRepository: AccountDataRepository,
    private val mapper: AccountMapper
) : AccountRepository {

    override suspend fun isSignIn(): Boolean {
        return accountDataRepository.isSignIn()
    }

    override suspend fun signOut() {
        accountDataRepository.signOut()
    }

    override suspend fun getCurrentAccountNameAndId(): AccountIdAndNameEntity {
        return mapper.toAccountIdAndNameEntity(accountDataRepository.getCurrentAccountIdAndName())
    }

    override suspend fun signIn(signIn: SignInEntity) {
        accountDataRepository.signIn(mapper.toSignInDataEntity(signIn))
    }

    override suspend fun signUp(signUpEntity: SignUpEntity) {
        accountDataRepository.signUp(mapper.toSignUpDataEntity(signUpEntity))
    }

    override suspend fun getAccountById(accountId: String): Flow<Resource<AccountEntity>> {
        return accountDataRepository.getAccountById(accountId).map { resource ->
            resource.map { account ->
                mapper.toAccount(account)
            }
        }
    }

    override suspend fun addImage(imageId: String) {
        accountDataRepository.addImage(imageId)
    }

    override fun update() {
        accountDataRepository.update()
    }

    override fun delete() {
        accountDataRepository.delete()
    }

    override fun reload() {
        accountDataRepository.reload()
    }

}