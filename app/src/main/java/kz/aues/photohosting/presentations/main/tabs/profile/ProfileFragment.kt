@file:Suppress("DEPRECATION")

package kz.aues.photohosting.presentations.main.tabs.profile

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentProfileBinding
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.BaseScreen
import kz.samsungcampus.android.viewModelCreator
import kz.samsungcampus.common.Resource
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()

    @Inject
    lateinit var viewModelFactory: ProfileViewModel.Factory

    private val viewModel by viewModelCreator {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU)
            viewModelFactory.create(
                arguments?.getSerializable(ARG_KEY, Screen::class.java) ?: Screen(null)
            )
        else {
            viewModelFactory.create(
                arguments?.getSerializable(ARG_KEY) as? Screen ?: Screen(null)
            )
        }
    }

    private lateinit var adapter: ProfileImagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        binding.observeState()
    }

    private fun initRecyclerview() {
        adapter = ProfileImagesAdapter()
        binding.imagesRecyclerview.adapter = adapter
        binding.imagesRecyclerview.layoutManager = GridLayoutManager(context, 3)
    }

    private fun FragmentProfileBinding.observeState() {
        viewModel.stateFlow.flowWithLifecycle(lifecycle)
            .onEach { resource ->
                contentContainer.isVisible = resource is Resource.Success
                progressContainer.root.isVisible = resource is Resource.Pending
                tryAgainContainer.root.isVisible = resource is Resource.Error

                if (resource !is Resource.Success) return@onEach
                val state = resource.value
                nameTextView.text = state.name
                countFollowersTextView.text = state.countFollowers
                countSubscribesTextView.text = state.countFollowers
                avatarImageView.load(state.avatarUri) {
                    transformations(CircleCropTransformation())
                }

                imagesRecyclerview.isVisible = state.previews is Resource.Success
                imagesProgressBar.isVisible = state.previews is Resource.Pending
                imagesTryAgainContainer.root.isVisible = state.previews is Resource.Error

                if (state.previews !is Resource.Success) return@onEach
                val previews = state.previews.value
                adapter.submitList(previews)
            }
    }

    class Screen(
        val id: String?
    ) : BaseScreen

    companion object {
        const val ARG_KEY = "arg key"
    }
}

