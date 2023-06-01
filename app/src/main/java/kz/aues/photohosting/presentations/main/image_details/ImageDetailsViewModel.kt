package kz.aues.photohosting.presentations.main.image_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.aues.photohosting.R
import kz.aues.photohosting.domain.ImageRemoteRepository
import kz.aues.photohosting.domain.main.tabs.images.models.ImageEntity
import kz.aues.photohosting.navigation.GlobalNavigationComponentRouter
import kz.aues.photohosting.presentations.main.add_comments.AddCommentDialogFragment
import kz.aues.photohosting.presentations.main.comments.CommentsFragment
import kz.samsungcampus.common.Resource

class ImageDetailsViewModel @AssistedInject constructor(
    @Assisted private val screen: ImageDetailsFragment.Screen,
    private val repository: ImageRemoteRepository,
    private val router: GlobalNavigationComponentRouter
) : ViewModel() {

    private var _image = MutableStateFlow<Resource<ImageEntity>>(Resource.Pending)
    val image = _image.asStateFlow()

    init {
        viewModelScope.launch {
           repository.getImageById(screen.id).collect { resource ->
               _image.value = resource
           }
        }
    }

    fun launchAddCommentDialog() {
        router.launch(
            destinationId = R.id.addCommentDialog,
            args = AddCommentDialogFragment.Screen(screen.id, false)
        )
    }

    fun launchCommentsFragment() {
        router.launch(
            destinationId = R.id.commentsFragment,
            args = CommentsFragment.Screen(screen.id)
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: ImageDetailsFragment.Screen): ImageDetailsViewModel
    }
}