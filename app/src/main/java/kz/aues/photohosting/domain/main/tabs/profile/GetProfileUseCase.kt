package kz.aues.photohosting.domain.main.tabs.profile

import kotlinx.coroutines.flow.Flow
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountEntity
import kz.samsungcampus.common.Resource
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend fun getAccount(id: String?): Flow<Resource<AccountEntity>> {
        val accountId = id ?: accountRepository.getCurrentAccountNameAndId().id
        return  accountRepository.getAccountById(accountId)
    }

}

