package kz.samsungcampus.common.flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kz.samsungcampus.common.Resource

typealias ValueLoader<T> = suspend () -> T

fun <T> lazyFlowSubject(
    dispatcher: CoroutineDispatcher,
    block: suspend () -> Resource<T>
): Flow<Resource<T>> =
    callbackFlow {
        var scope: CoroutineScope? = CoroutineScope(SupervisorJob() + dispatcher)
        send(Resource.Pending)

        val job = scope?.launch {
            send(block())
        }

        awaitClose {
            scope = null
            job?.cancel()
        }
    }


class LazyFlowSubject<T>(
    private var valueLoader: ValueLoader<T>,
    private val dispatcher: CoroutineDispatcher,
    private val globalScope: CoroutineScope
) {

    private var count = 0
    private var scope: CoroutineScope? = null
    private var cancellationJob: Job? = null
    private var inputFlow = MutableStateFlow<Value<T>>(Value.LoadValue(valueLoader))
    private val outputFlow = MutableStateFlow<Resource<T>>(Resource.Pending)

    private val mutex = Mutex()

    fun listen(): Flow<Resource<T>> = callbackFlow {
        synchronized(this@LazyFlowSubject) {
            onStart()
        }

        val job = scope?.launch {
            outputFlow.collect {
                trySend(it)
            }
        }

        awaitClose {
            synchronized(this@LazyFlowSubject) {
                onStop(job)
            }
        }
    }

    suspend fun newLoad(silently: Boolean, valueLoader: ValueLoader<T>?): T = mutex.withLock {
        val completableDeferred = prepareNewLoad(
            createdCompletableDeferred = true,
            silently = silently,
            valueLoader = valueLoader
        )
        completableDeferred!!.await()
    }

    fun newAsyncLoad(silently: Boolean = false, valueLoader: ValueLoader<T>? = null) {
        prepareNewLoad(
            createdCompletableDeferred = false,
            silently = silently,
            valueLoader = valueLoader
        )
    }

    private fun prepareNewLoad(createdCompletableDeferred: Boolean, silently: Boolean, valueLoader: ValueLoader<T>?): CompletableDeferred<T>? {
        val oldLoad = inputFlow.value
        if (oldLoad is Value.LoadValue && oldLoad.completableDeferred?.isActive == true) {
            oldLoad.completableDeferred.cancel()
        }
        if (valueLoader != null) {
            this.valueLoader = valueLoader
        }
        val completableDeferred = if (createdCompletableDeferred) {
            CompletableDeferred<T>()
        } else {
            null
        }
        inputFlow.value = Value.LoadValue(this.valueLoader, silently, completableDeferred)
        return completableDeferred
    }

    fun updateWith(Resource: Resource<T>) {
        inputFlow.value = Value.InstantValue(Resource)
    }

    fun updateWith(updater: (Resource<T>) -> Resource<T>) {
        val oldValue = outputFlow.value
        inputFlow.value = Value.InstantValue(updater(oldValue))
    }

    private fun onStart() {
        count++
        if (count == 1) {
            cancellationJob?.cancel()
            startLoading()
        }
    }

    private fun onStop(job: Job?) {
        count--
        job?.cancel()
        if (count == 0) {
            cancellationJob = globalScope.launch {
                synchronized(this@LazyFlowSubject) {
                    if (count == 0) { // double check required
                        scope?.cancel()
                        scope = null
                    }
                }
            }
        }
    }

    private fun startLoading() {
        if (scope != null) return

        outputFlow.value = Resource.Pending
        scope = CoroutineScope(SupervisorJob() + dispatcher)
        scope?.launch {
            inputFlow
                .collectLatest {
                    when (it) {
                        is Value.InstantValue -> outputFlow.value = it.Resource
                        is Value.LoadValue -> loadValue(it)
                    }
                }
        }
    }

    private suspend fun loadValue(loadValue: Value.LoadValue<T>) {
        try {
            if (!loadValue.silent) outputFlow.value = Resource.Pending
            val value = loadValue.loader()
            outputFlow.value = Resource.Success(value)
            mutex.withLock {
                loadValue.completableDeferred?.complete(value)
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            outputFlow.value = Resource.Error(e)
            mutex.withLock {
                loadValue.completableDeferred?.completeExceptionally(e)
            }
        }
    }

    sealed class Value<T> {
        class InstantValue<T>(val   Resource: Resource<T>) : Value<T>()
        class LoadValue<T>(
            val loader: ValueLoader<T>,
            val silent: Boolean = false,
            val completableDeferred: CompletableDeferred<T>? = null
        ) : Value<T>()
    }
}

