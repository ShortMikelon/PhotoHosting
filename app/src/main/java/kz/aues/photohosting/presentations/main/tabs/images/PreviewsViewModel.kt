package kz.aues.photohosting.presentations.main.tabs.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.aues.photohosting.R
import kz.aues.photohosting.domain.PreviewsRepository
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.aues.photohosting.navigation.GlobalNavigationComponentRouter
import kz.aues.photohosting.presentations.main.image_details.ImageDetailsFragment
import kz.samsungcampus.common.Resource
import javax.inject.Inject

@HiltViewModel
class PreviewsViewModel @Inject constructor(
    private val repository: PreviewsRepository,
    private val router: GlobalNavigationComponentRouter
) : ViewModel() {

    private val _previews = MutableStateFlow<Resource<List<PreviewEntity>>>(Resource.Pending)
    val preview get() = _previews.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getPreviews().collect {
                if (_previews.value != it) _previews.emit(it)
            }
        }
    }

    fun launchImageDetails(previewEntity: PreviewEntity) {
        router.launch(
            destinationId = R.id.imageDetailsFragment,
            args = ImageDetailsFragment.Screen(previewEntity.id)
        )
    }
}