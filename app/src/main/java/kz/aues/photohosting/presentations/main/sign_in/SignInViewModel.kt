package kz.aues.photohosting.presentations.main.sign_in

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kz.aues.photohosting.R
import kz.aues.photohosting.domain.exceptions.NetworkException
import kz.aues.photohosting.domain.main.sign_in.SignInUseCase
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_in.entities.SignInField
import kz.aues.photohosting.domain.main.sign_in.exceptions.AuthInvalidEmailException
import kz.aues.photohosting.domain.main.sign_in.exceptions.AuthInvalidPasswordException
import kz.aues.photohosting.domain.main.sign_in.exceptions.EmptyFieldException
import kz.aues.photohosting.navigation.GlobalNavigationComponentRouter
import kz.aues.photohosting.utlis.createWarningSnackbarConfig
import kz.samsungcampus.android.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val router: GlobalNavigationComponentRouter
) : BaseViewModel() {

    private val fieldErrorStateFlow = MutableStateFlow<Pair<SignInField, String>?>(null)
    private val isSignInInProcessStateFlow = MutableStateFlow(false)

    private val _focusFieldLiveEvent = MutableLiveEvent<SignInField>()
    private val _clearPasswordFieldLiveEvent = MutableLiveEvent<Unit>()
    private val _snackbarLiveEvent = MutableLiveEvent<SnackbarConfig>()

    val focusFieldLiveEvent get() = _focusFieldLiveEvent.share()
    val clearPasswordFieldLiveEvent get() = _clearPasswordFieldLiveEvent.share()
    val snackbarLiveEvent get() = _snackbarLiveEvent.share()

    val stateFlow = combine(
        fieldErrorStateFlow,
        isSignInInProcessStateFlow,
        ::State
    )

    fun signIn(signInEntity: SignInEntity) {
        viewModelScope.launch {
            try {
                showProgress()
                signInUseCase.signIn(signInEntity)
                router.startTabs()
            } catch (ex: EmptyFieldException) {
                handleEmptyFieldException(ex)
            } catch (ex: AuthInvalidEmailException) {
                handleAuthInvalidEmailException()
            } catch (ex: AuthInvalidPasswordException) {
                handleAuthInvalidPasswordException()
            } catch (ex: NetworkException) {
                handleNetworkException()
            } catch (ex: Exception) {
                Log.d("TAGGG", ex.toString())
            } finally {
                hideProgress()
            }
        }
    }

    fun signUp() {
        router.launch(R.id.signUpFragment)
    }

    fun clearError(field: SignInField) {
        val currentFieldError = fieldErrorStateFlow.value?.first
        if (field == currentFieldError) {
            fieldErrorStateFlow.value = null
        }
    }

    private fun handleEmptyFieldException(ex: EmptyFieldException) {
        val field = ex.field
        _focusFieldLiveEvent.publishEvent(field)
        fieldErrorStateFlow.value = field to resources.getString(R.string.empty_field)
    }

    private fun handleAuthInvalidEmailException() {
        _focusFieldLiveEvent.publishEvent(SignInField.EMAIL)
        fieldErrorStateFlow.value = SignInField.EMAIL to resources.getString(R.string.account_not_found)
    }

    private fun handleAuthInvalidPasswordException() {
        _focusFieldLiveEvent.publishEvent(SignInField.PASSWORD)
        _clearPasswordFieldLiveEvent.publishEvent(Unit)
        fieldErrorStateFlow.value = SignInField.PASSWORD to resources.getString(R.string.wrong_password)
    }

    private fun handleNetworkException() {
        _snackbarLiveEvent.publishEvent(createWarningSnackbarConfig(resources.getString(R.string.bad_internet)))
    }

    private fun showProgress() {
        isSignInInProcessStateFlow.value = true
    }

    private fun hideProgress() {
        isSignInInProcessStateFlow.value = false
    }

    data class State(
        val fieldError: Pair<SignInField, String>?,
        val isSignInInProcess: Boolean
    ) {
        val enableSignInButton get() = !isSignInInProcess
        val showProgress get() = isSignInInProcess
    }

}