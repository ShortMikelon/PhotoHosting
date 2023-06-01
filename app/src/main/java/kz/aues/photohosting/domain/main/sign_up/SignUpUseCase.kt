package kz.aues.photohosting.domain.main.sign_up

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kz.aues.photohosting.data.account.exceptions.AccountAlreadyExistsDataException
import kz.aues.photohosting.data.account.exceptions.WeakPasswordDataException
import kz.aues.photohosting.domain.AccountRepository
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntity
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntityWithoutAvatar
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpField
import kz.aues.photohosting.domain.main.sign_up.exceptions.*
import kz.aues.photohosting.utlis.getBytesFromUri
import kz.samsungcampus.common.extensions.getExtensionFromUrl
import kz.samsungcampus.common.extensions.isValidEmail
import java.util.*
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: AccountRepository
) {

    suspend fun signUp(signUpEntityWithoutAvatar: SignUpEntityWithoutAvatar) {
        signUpEntityWithoutAvatar.validate()

        val bytes = if (signUpEntityWithoutAvatar.avatarUri == SignUpEntity.NO_AVATAR) {
            SignUpEntity.DEFAULT_AVATAR
        } else {
            getBytesFromUri(context, signUpEntityWithoutAvatar.avatarUri)
        }
        val avatarExtension = signUpEntityWithoutAvatar.avatarUri.getExtensionFromUrl()
        val avatarName = "${UUID.randomUUID()}.$avatarExtension"
        Log.d("TAGGG", "avatarName = $avatarName\navatarExtensions = $avatarExtension")

        val signUpEntity = SignUpEntity(
            name = signUpEntityWithoutAvatar.name,
            email = signUpEntityWithoutAvatar.email,
            password = signUpEntityWithoutAvatar.password,
            avatarUniqueName = avatarName,
            avatar = bytes
        )

        try {
            repository.signUp(signUpEntity)
        } catch (e: AccountAlreadyExistsDataException) {
            throw AccountAlreadyExistsException()
        } catch (e: WeakPasswordDataException) {
            throw WeakPasswordException()
        }
    }

    private fun SignUpEntityWithoutAvatar.validate() {
        if (email.isEmpty()) throw EmptyFieldException(SignUpField.EMAIL)
        if (name.isEmpty()) throw EmptyFieldException(SignUpField.USERNAME)
        if (password.isEmpty()) throw EmptyFieldException(SignUpField.PASSWORD)
        if (repeatPassword.isEmpty()) throw EmptyFieldException(SignUpField.REPEAT_PASSWORD)
        if (!email.isValidEmail()) throw IncorrectEmailException()
        if (password != repeatPassword) throw PasswordMismatchException()
    }

}

