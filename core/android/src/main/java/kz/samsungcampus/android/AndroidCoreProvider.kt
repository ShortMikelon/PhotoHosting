package kz.samsungcampus.android

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kz.samsungcampus.common.CommonUi
import kz.samsungcampus.common.CoreProvider
import kz.samsungcampus.common.SystemResources

class AndroidCoreProvider(
    private val context: Context,
    override val globalScope: CoroutineScope = createDefaultGlobalScope(),
    override val resources: SystemResources = AndroidResources(context),
    override val commonUi: CommonUi = AndroidUi(context)
) : CoreProvider


private fun createDefaultGlobalScope() =
    CoroutineScope(SupervisorJob() + Dispatchers.Main)