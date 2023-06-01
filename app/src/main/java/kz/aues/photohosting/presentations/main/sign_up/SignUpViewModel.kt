package kz.aues.photohosting.presentations.main.sign_up

import android.net.Uri
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kz.aues.photohosting.R
import kz.aues.photohosting.domain.main.sign_up.SignUpUseCase
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntity
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpEntityWithoutAvatar
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpField
import kz.aues.photohosting.domain.main.sign_up.exceptions.*
import kz.aues.photohosting.navigation.GlobalNavigationComponentRouter
import kz.aues.photohosting.utlis.createSnackbarConfig
import kz.aues.photohosting.utlis.createWarningSnackbarConfig
import kz.samsungcampus.android.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val router: GlobalNavigationComponentRouter
) : BaseViewModel() {

    private val fieldErrorFlow = MutableStateFlow<Pair<SignUpField, String>?>(null)
    private val isSignUpInProcessFlow = MutableStateFlow(false)
    private val avatarUriFlow = MutableStateFlow<Uri?>(null)

    private val _focusFieldLiveData = MutableLiveEvent<SignUpField>()
    private val _clearFieldLiveData = MutableLiveEvent<SignUpField>()
    private val _initialEmailFieldLiveData = MutableLiveEvent<String>()
    private val _snackbarLiveData = MutableLiveEvent<SnackbarConfig>()

    val focusFieldLiveData = _focusFieldLiveData.share()
    val clearFieldLiveData = _clearFieldLiveData.share()
    val initialEmailFieldLiveData = _initialEmailFieldLiveData.share()
    val snackbarLiveData = _snackbarLiveData.share()

    val stateFlow = combine(
        fieldErrorFlow,
        isSignUpInProcessFlow,
        avatarUriFlow,
        ::State
    )

    fun processResultGallery(uri: Uri?) {
        if (uri == null) {
            _snackbarLiveData.publishEvent(createWarningSnackbarConfig(resources.getString(R.string.failed_image_load)))
        } else {
            avatarUriFlow.value = uri
        }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ) {
        val signUpEntityWithoutAvatar = SignUpEntityWithoutAvatar(
            name = name,
            email = email,
            password = password,
            repeatPassword = repeatPassword,
            avatarUri = avatarUriFlow.value?.toString() ?: SignUpEntity.NO_AVATAR
        )
        viewModelScope.launch {
            try {
                showProgress()
                signUpUseCase.signUp(signUpEntityWithoutAvatar)
                showSuccessSnackbar()
                router.pop()
            } catch (ex: EmptyFieldException) {
                handleEmptyFieldException(ex)
            } catch (ex: WeakPasswordException) {
                handleWeakPasswordException()
            } catch (ex: PasswordMismatchException) {
                handlePasswordMismatchException()
            } catch (ex: AccountAlreadyExistsException) {
                handleAccountAlreadyExistsException()
            } catch (ex: IncorrectEmailException) {
                handleIncorrectEmailException()
            } finally {
                hideProgress()
            }
        }
    }

    fun clearError(field: SignUpField) {
        val currentField = fieldErrorFlow.value?.first
        if (currentField == field) {
            fieldErrorFlow.value = null
        }
    }

    private fun handleEmptyFieldException(exception: EmptyFieldException) {
        val field = exception.field
        _focusFieldLiveData.publishEvent(field)
        setFieldError(field, resources.getString(R.string.empty_field))
    }

    private fun handleWeakPasswordException() {
        _focusFieldLiveData.publishEvent(SignUpField.PASSWORD)
        setFieldError(SignUpField.PASSWORD, resources.getString(R.string.weak_password))
    }

    private fun handlePasswordMismatchException() {
        _focusFieldLiveData.publishEvent(SignUpField.REPEAT_PASSWORD)
        _clearFieldLiveData.publishEvent(SignUpField.REPEAT_PASSWORD)
        setFieldError(SignUpField.REPEAT_PASSWORD, resources.getString(R.string.password_warning))
    }

    private fun handleAccountAlreadyExistsException() {
        val config =
            createWarningSnackbarConfig(resources.getString(R.string.account_already_exists))
        _snackbarLiveData.publishEvent(config)
    }

    private fun handleIncorrectEmailException() {
        _focusFieldLiveData.publishEvent(SignUpField.EMAIL)
        setFieldError(SignUpField.EMAIL, resources.getString(R.string.incorrect_email))
    }

    private fun setFieldError(field: SignUpField, errorMessage: String) {
        fieldErrorFlow.value = field to errorMessage
    }

    private fun showSuccessSnackbar() {
        _snackbarLiveData.publishEvent(createSnackbarConfig(resources.getString(R.string.success_sign_up)))
    }

    private fun showProgress() {
        isSignUpInProcessFlow.value = true
    }

    private fun hideProgress() {
        isSignUpInProcessFlow.value = false
    }

    data class State(
        val fieldError: Pair<SignUpField, String>?,
        val isSignUpInProcess: Boolean,
        val avatarUri: Uri?,
    ) {
        val showProgress get() = isSignUpInProcess
        val enableSignUpButton get() = !isSignUpInProcess
        val showAvatar get() = avatarUri != null
    }

}