package kz.aues.photohosting.presentations.main.tabs.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.aues.photohosting.domain.main.tabs.profile.GetProfileUseCase
import kz.aues.photohosting.domain.main.tabs.profile.GetUserPreviewsUseCase
import kz.aues.photohosting.domain.main.tabs.profile.entities.AccountEntity
import kz.samsungcampus.common.Resource

class ProfileViewModel @AssistedInject constructor(
    @Assisted private val screen: ProfileFragment.Screen,
    private val getProfileUseCase: GetProfileUseCase,
    private val getUserPreviews: GetUserPreviewsUseCase
) : ViewModel() {

    private val accountStateFlow = MutableStateFlow<Resource<AccountEntity>>(Resource.Pending)
    private var previewsStateFlow =
        MutableStateFlow<Resource<List<PreviewEntity>>>(Resource.Pending)

    val stateFlow = combine(
        accountStateFlow,
        previewsStateFlow,
        ::merge
    )

    init {
        viewModelScope.launch {
            getProfileUseCase.getAccount(screen.id).collect {
                accountStateFlow.value = it
            }
        }
        viewModelScope.launch {
            val resource = accountStateFlow.first { it is Resource.Success } as Resource.Success
            val account = resource.value
            getUserPreviews.getPreviews(account.id).collect {
                previewsStateFlow.value = it
            }
        }
    }

    private fun merge(
        account: Resource<AccountEntity>,
        previews: Resource<List<PreviewEntity>>
    ): Resource<State> {
        return account.map {
            State(
                id = it.id,
                name = it.name,
                avatarUri = it.avatarUri,
                followersIds = it.followersIds,
                subscribesIds = it.subscribesIds,
                previews = previews
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(screen: ProfileFragment.Screen): ProfileViewModel
    }

    class State(
        val id: String,
        val name: String,
        val avatarUri: String,
        val followersIds: List<String>,
        val subscribesIds: List<String>,
        val previews: Resource<List<PreviewEntity>>,
    ) {
        val countFollowers get() = followersIds.size.toString()
        val countSubscribes get() = subscribesIds.size.toString()
    }
}