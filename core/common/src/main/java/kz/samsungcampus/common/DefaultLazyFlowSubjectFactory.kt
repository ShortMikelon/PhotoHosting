package kz.samsungcampus.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kz.samsungcampus.common.flow.LazyFlowSubject
import kz.samsungcampus.common.flow.LazyFlowSubjectFactory
import kz.samsungcampus.common.flow.ValueLoader

class DefaultLazyFlowSubjectFactory(
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope = Core.globalScope,
) : LazyFlowSubjectFactory {
    override fun <T> create(valueLoader: ValueLoader<T>) =
        LazyFlowSubject(
            valueLoader = valueLoader,
            dispatcher = dispatcher,
            globalScope = globalScope
        )
}