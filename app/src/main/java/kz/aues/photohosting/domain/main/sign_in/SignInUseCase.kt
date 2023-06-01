package kz.aues.photohosting.domain.main.sign_in

import kz.aues.photohosting.data.account.exceptions.AuthInvalidEmailDataException
import kz.aues.photohosting.data.account.exceptions.AuthInvalidPasswordDataException
import kz.aues.photohosting.data.exceptions.NetworkDataException
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.exceptions.NetworkException
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_in.entities.SignInField
import kz.aues.photohosting.domain.main.sign_in.exceptions.AuthInvalidEmailException
import kz.aues.photohosting.domain.main.sign_in.exceptions.AuthInvalidPasswordException
import kz.aues.photohosting.domain.main.sign_in.exceptions.EmptyFieldException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AccountRepository
) {

    suspend fun signIn(signInEntity: SignInEntity) {
        signInEntity.isValid()

        try {
            repository.signIn(signInEntity)
        } catch (ex: AuthInvalidEmailDataException) {
            throw AuthInvalidEmailException()
        } catch (ex: AuthInvalidPasswordDataException) {
            throw AuthInvalidPasswordException()
        } catch (ex: NetworkDataException) {
            throw NetworkException()
        }
    }

    private fun SignInEntity.isValid() {
        if (this.email.isEmpty()) throw EmptyFieldException(SignInField.EMAIL)
        if (this.password.isEmpty()) throw EmptyFieldException(SignInField.PASSWORD)
    }
}