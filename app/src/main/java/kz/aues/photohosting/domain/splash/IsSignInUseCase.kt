package kz.aues.photohosting.domain.splash

import kz.aues.photohosting.domain.AccountRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IsSignInUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun isSignIn(): Boolean {
        return repository.isSignIn()
    }

}