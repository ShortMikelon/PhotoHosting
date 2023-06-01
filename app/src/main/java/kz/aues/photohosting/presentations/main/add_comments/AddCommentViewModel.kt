package kz.aues.photohosting.presentations.main.add_comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kz.aues.photohosting.domain.main.add_comment.SendCommentUseCase

class AddCommentViewModel @AssistedInject constructor(
    @Assisted private val screen: AddCommentDialogFragment.Screen,
    private val sendCommentUseCase: SendCommentUseCase
): ViewModel() {

    private val _sendingStatus = MutableLiveData<Boolean>()
    val sendingStatus: LiveData<Boolean> = _sendingStatus

    fun sendComment(text: String) {
        viewModelScope.launch {
            _sendingStatus.value = sendCommentUseCase.send(text, screen.imageId, screen.shouldCommentsUpdated)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: AddCommentDialogFragment.Screen): AddCommentViewModel
    }
}

