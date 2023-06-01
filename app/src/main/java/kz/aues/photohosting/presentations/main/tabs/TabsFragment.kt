package kz.aues.photohosting.presentations.main.tabs

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentTabsBinding
import kz.aues.photohosting.navigation.DestinationsProvider
import kz.aues.photohosting.navigation.NavigationMode
import kz.aues.photohosting.navigation.NavigationModeHolder
import kz.aues.photohosting.presentations.main.tabs.profile.ProfileFragment
import kz.aues.photohosting.utlis.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class TabsFragment : Fragment(R.layout.fragment_tabs) {

    @Inject
    lateinit var destinationsProvider: DestinationsProvider

    @Inject
    lateinit var navigationModeHolder: NavigationModeHolder

    private val binding by viewBinding<FragmentTabsBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationMode = navigationModeHolder.navigationMode
        if (navigationMode is NavigationMode.Tabs) {
            val menu = binding.bottomNavigationView.menu
            navigationMode.tabs.forEach { tab ->
                val menuItem = menu.add(0, tab.destinationId, Menu.NONE, tab.title)
                menuItem.setIcon(tab.icon)
            }

            val navHost =
                childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
            val navController = navHost.navController
            val graph =
                navController.navInflater.inflate(destinationsProvider.provideNavigationGraphId())
            graph.setStartDestination(
                navigationMode.startTabDestinationId
            )
            navController.graph = graph
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        }

    }
}