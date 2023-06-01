package kz.aues.photohosting.data.utils

import kz.samsungcampus.common.AppException

class StorageDataException(
    override val message: String
) : AppException(message) {

    companion object {
        const val SERVER_EXCEPTION_MESSAGE = "SERVER_EXCEPTION_MESSAGE"
        const val LOCAL_EXCEPTION_MESSAGE = "LOCAL_EXCEPTION_MESSAGE"
    }
}