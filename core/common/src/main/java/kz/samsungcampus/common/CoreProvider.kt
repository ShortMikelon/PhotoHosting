package kz.samsungcampus.common

import kotlinx.coroutines.CoroutineScope

interface CoreProvider {

    val globalScope: CoroutineScope

    val resources: SystemResources

    val commonUi: CommonUi

}

