package kz.aues.photohosting.utlis

import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.samsungcampus.common.Resource

fun <T> Fragment.resultObserve(
    flow: Flow<Resource<T>>,
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit = { },
    onPending: () -> Unit = { }
) {
    flow.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
        .onEach {
            it.complete(onSuccess, onError, onPending)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
}

fun <T> Fragment.resultObserve(
    liveData: LiveData<Resource<T>>,
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit = { },
    onPending: () -> Unit = { }
) {
    liveData.observe(viewLifecycleOwner) {
        it.complete(onSuccess, onError, onPending)
    }
}

fun Fragment.dpToPx(dp: Float): Int {
    val density = requireContext().applicationContext.resources.displayMetrics.density
    return (dp * density + 0.5f).toInt()
}

fun Fragment.dpToPx(@DimenRes dimenRes: Int): Int {
    val dp = resources.getDimension(dimenRes)
    val density = requireContext().applicationContext.resources.displayMetrics.density
    return (dp * density + 0.5f).toInt()
}