package kz.aues.photohosting.presentations.main.tabs.create_image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kz.aues.photohosting.R
import kz.aues.photohosting.domain.main.tabs.create_image.CreateImageUseCase
import kz.aues.photohosting.domain.main.tabs.create_image.entities.CreateImageEntity
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.EmptyNameFieldException
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.FailedCreateImageException
import kz.aues.photohosting.domain.main.tabs.create_image.exceptions.InvalidUrlException
import kz.aues.photohosting.utlis.createWarningSnackbarConfig
import kz.samsungcampus.android.MutableLiveEvent
import kz.samsungcampus.android.SnackbarConfig
import kz.samsungcampus.android.publishEvent
import kz.samsungcampus.android.share
import kz.samsungcampus.common.Core
import kz.samsungcampus.common.Core.resources
import javax.inject.Inject

@HiltViewModel
class CreateImageViewModel @Inject constructor(
    private val createImageUseCase: CreateImageUseCase
) : ViewModel() {

    private val imageUrlStateFlow = MutableStateFlow("")
    private val isCreateImageInProcessStateFlow = MutableStateFlow(false)

    private val _nameFieldErrorLiveEvent = MutableLiveEvent<String>()
    private val _snackbarLiveEvent = MutableLiveEvent<SnackbarConfig>()

    val nameFieldErrorLiveEvent get() = _nameFieldErrorLiveEvent.share()
    val snackbarLiveEvent get() = _snackbarLiveEvent.share()

    val stateFlow = combine(
        imageUrlStateFlow,
        isCreateImageInProcessStateFlow,
        ::State
    )

    fun processGalleryResult(uri: Uri?) {
        if (uri == null) {
            _snackbarLiveEvent.publishEvent(createWarningSnackbarConfig(resources.getString(R.string.failed_image_load)))
        } else {
            imageUrlStateFlow.value = uri.toString()
        }

    }

    fun createImage(
        name: String,
        description: String
    ) {
        viewModelScope.launch {
            try {
                showProgress()
                createImageUseCase.createImage(
                    CreateImageEntity(
                        name,
                        description,
                        imageUrlStateFlow.value
                    )
                )
            } catch (ex: EmptyNameFieldException) {
                handleEmptyNameFieldException()
            } catch (ex: InvalidUrlException) {
                handleInvalidUrlException()
            } catch (ex: FailedCreateImageException) {
                handleFailedCreateImageException()
            } finally {
                hideProgress()
            }
        }
    }

    private fun handleEmptyNameFieldException() {
        _nameFieldErrorLiveEvent.publishEvent(resources.getString(R.string.empty_field))
    }

    private fun handleInvalidUrlException() {
        _snackbarLiveEvent.publishEvent(
            createWarningSnackbarConfig(resources.getString(R.string.invalid_image))
        )
    }

    private fun handleFailedCreateImageException() {
        _snackbarLiveEvent.publishEvent(
            createWarningSnackbarConfig(resources.getString(R.string.failed_create_image))
        )
    }

    private fun showProgress() {
        isCreateImageInProcessStateFlow.value = true
    }

    private fun hideProgress() {
        isCreateImageInProcessStateFlow.value = false
    }

    data class State(
        val imageUrl: String,
        val isCreateImageInProgress: Boolean
    ) {
        val showImageView get() = imageUrl != ""
        val enableButtons get() = !isCreateImageInProgress
        val showProgress get() = isCreateImageInProgress
    }

}