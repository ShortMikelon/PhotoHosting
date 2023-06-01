package kz.aues.photohosting.utlis

import kz.samsungcampus.common.Resource

fun <T> Resource<T>.complete(
    onSuccess: (T) -> Unit,
    onError: (Exception) -> Unit,
    onPending: () -> Unit
) {
    when (this) {
        is Resource.Success -> onSuccess(this.value)
        is Resource.Error -> onError(exception)
        is Resource.Pending -> onPending()
    }
}