package kz.aues.photohosting.presentations.main

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.ActivityMainBinding
import kz.aues.photohosting.navigation.DestinationsProvider
import kz.aues.photohosting.navigation.NavComponentRouter
import kz.aues.photohosting.navigation.RouterHolder
import kz.samsungcampus.android.ActivityRequired
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RouterHolder {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

    @Inject
    lateinit var destinationProvider: DestinationsProvider

    @Inject
    lateinit var activityRequired: Set<@JvmSuppressWildcards ActivityRequired>

    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
        navComponentRouterFactory.create(R.id.fragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navComponentRouter.onCreate()
        if (savedInstanceState != null) {
            navComponentRouter.onRestoreInstanceState(savedInstanceState)
        } else if (isSignedIn()) {
            navComponentRouter.switchToTabs(
                startTabDestinationId = destinationProvider.provideTabsStartDestination(),
                rootDestinations = destinationProvider.provideMainTabs()
            )
        } else {
            navComponentRouter.switchToStack(
                destinationProvider.provideStartDestinationId()
            )
        }

        activityRequired.forEach { it.onCreate(this) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navComponentRouter.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        activityRequired.forEach { it.onStart() }
    }

    override fun onStop() {
        super.onStop()
        activityRequired.forEach { it.onStopped() }
    }

    override fun onDestroy() {
        super.onDestroy()
        navComponentRouter.onDestroy()
        activityRequired.forEach { it.onDestroy() }
    }

    override fun requireRouter(): NavComponentRouter {
        return navComponentRouter
    }

    private fun isSignedIn(): Boolean {
        val bundle = intent.extras ?: throw IllegalStateException("No required arguments")
        val args = MainActivityArgs.fromBundle(bundle)
        return args.isSignedIn
    }

    @IdRes
    private fun provideStartTabDestination(): Int = R.id.previewsFragment
}