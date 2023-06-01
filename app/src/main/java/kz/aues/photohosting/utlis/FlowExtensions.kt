package kz.aues.photohosting.utlis

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.samsungcampus.common.Resource

fun <T> Flow<Resource<T>>.resultObserve(
    lifecycle: Lifecycle,
    scope: CoroutineScope,
    onSuccess: (T) -> Unit,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onError: (() -> Unit),
    onPending: (() -> Unit)
) {
    this.flowWithLifecycle(lifecycle, minActiveState)
        .onEach { state ->
            when (state) {
                is Resource.Success -> onSuccess(state.value)
                is Resource.Error -> onError()
                is Resource.Pending -> onPending()
            }
        }.launchIn(scope)
}