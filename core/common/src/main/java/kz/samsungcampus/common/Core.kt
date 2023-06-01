package kz.samsungcampus.common

import kotlinx.coroutines.CoroutineScope

object Core {

    private lateinit var coreProvider: CoreProvider

    val globalScope: CoroutineScope get() = coreProvider.globalScope

    val resources: SystemResources get() = coreProvider.resources

    fun init(coreProvider: CoreProvider) {
        this.coreProvider = coreProvider
    }
}