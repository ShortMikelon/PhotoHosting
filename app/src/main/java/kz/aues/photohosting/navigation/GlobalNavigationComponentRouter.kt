package kz.aues.photohosting.navigation

import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kz.samsungcampus.android.ActivityRequired
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalNavigationComponentRouter @Inject constructor(
    private val destinationsProvider: DestinationsProvider
) : ActivityRequired {

    private var activity: FragmentActivity? = null
    private var started = false
    private var destroyed = true
    private val commands = mutableSetOf<() -> Unit>()

    private val onBackPressHandlers = mutableSetOf<() -> Boolean>()

    override fun onCreate(activity: FragmentActivity) {
        this.activity = activity
        this.destroyed = false
        setupBackHandlers()
    }

    override fun onStart() {
        started = true
        commands.forEach { it.invoke() }
        commands.clear()
    }

    override fun onStopped() {
        started = false
    }

    override fun onDestroy() {
        if (this.activity?.isFinishing == true) {
            destroyed = true
            commands.clear()
        }
    }

    fun registerOnBackHandler(scope: CoroutineScope, handler: () -> Boolean) {
        scope.launch {
            suspendCancellableCoroutine { continuation ->
                onBackPressHandlers.add(handler)
                continuation.invokeOnCancellation {
                    onBackPressHandlers.remove(handler)
                }
            }
        }
    }

    fun launch(@IdRes destinationId: Int, args: java.io.Serializable? = null) = invoke {
        requireRouter().launch(destinationId, args)
    }

    fun pop() = invoke {
        requireRouter().pop()
    }

    fun startTabs() = invoke {
        requireRouter().switchToTabs(
            rootDestinations = destinationsProvider.provideMainTabs(),
            startTabDestinationId = destinationsProvider.provideTabsStartDestination()
        )
    }

    private fun setupBackHandlers() {
        requireActivity().onBackPressedDispatcher.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {
                    if (requireRouter().isDialog) {
                        processAsUsual()
                        return
                    }
                    onBackPressHandlers.reversed().forEach { handler ->
                        if (handler.invoke()) return
                    }
                    processAsUsual()
                }

                private fun processAsUsual() {
                    isEnabled = false
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    isEnabled = true
                }
            })
    }

    private fun requireActivity() =
        activity!!

    private fun requireRouter() =
        (activity as RouterHolder).requireRouter()

    private fun invoke(command: () -> Unit) {
        if (!started) commands.add(command)
        else command.invoke()
    }

}

