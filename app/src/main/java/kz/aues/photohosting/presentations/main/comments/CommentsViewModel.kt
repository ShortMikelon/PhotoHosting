package kz.aues.photohosting.presentations.main.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.aues.photohosting.domain.CommentsRepository
import kz.aues.photohosting.domain.main.comments.entities.CommentEntity
import kz.samsungcampus.common.Resource

class CommentsViewModel @AssistedInject constructor(
    private val repository: CommentsRepository,
    @Assisted private val screen: CommentsFragment.Screen
) : ViewModel() {

    private val _comments = MutableStateFlow<Resource<List<CommentEntity>>>(Resource.Pending)
    val comments get() = _comments.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getComments(screen.imageId).collect {
                if (_comments.value != it) _comments.emit(it)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: CommentsFragment.Screen): CommentsViewModel
    }
}