package kz.aues.photohosting.presentations.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.aues.photohosting.domain.splash.IsSignInUseCase
import kz.samsungcampus.android.MutableLiveEvent
import kz.samsungcampus.android.publishEvent
import kz.samsungcampus.android.share
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isSignInUseCase: IsSignInUseCase
): ViewModel() {

    private val _launchMainActivityLiveData = MutableLiveEvent<Boolean>()
    val launchMainActivity get() = _launchMainActivityLiveData.share()

    init {
        viewModelScope.launch {
            _launchMainActivityLiveData.publishEvent(isSignInUseCase.isSignIn())
        }
    }
}