package kz.samsungcampus.common

sealed class Resource<out T> {

    fun <R> map(mapper: ((T) -> R)): Resource<R> {
        return when (this) {
            is Pending -> Pending
            is Error -> Error(this.exception)
            is Success -> Success(mapper(value))
        }
    }

    data class Success<T>(val value: T) : Resource<T>()

    data class Error(val exception: Exception) : Resource<Nothing>()

    object Pending : Resource<Nothing>()
}

