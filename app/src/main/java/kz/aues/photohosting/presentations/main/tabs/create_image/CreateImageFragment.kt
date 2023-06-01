package kz.aues.photohosting.presentations.main.tabs.create_image

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentCreateImageBinding
import kz.aues.photohosting.domain.main.tabs.create_image.entities.CreateImageEntity
import kz.aues.photohosting.utlis.show
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.observeEvent

@AndroidEntryPoint
class CreateImageFragment : Fragment(R.layout.fragment_create_image){

    private val binding by viewBinding<FragmentCreateImageBinding>()

    private val viewModel by viewModels<CreateImageViewModel>()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        viewModel.processGalleryResult(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        with(binding) {
            observeState()
            setupListeners()
        }
    }

    private fun observeEvents() {
        viewModel.snackbarLiveEvent.observeEvent(viewLifecycleOwner) {
            it.show(requireContext(), binding.root)
        }
        viewModel.nameFieldErrorLiveEvent.observeEvent(viewLifecycleOwner) { messageError ->
            binding.nameEditText.requestFocus()
            binding.nameEditText.error = messageError
        }
    }

    private fun FragmentCreateImageBinding.observeState() {
        viewModel.stateFlow.flowWithLifecycle(lifecycle)
            .onEach { state ->
                createImageButton.isEnabled = state.enableButtons
                progressBar.isVisible = state.showProgress
                if (state.imageUrl.isEmpty()) {
                    imageView.load(R.drawable.ic_click_to_select)
                } else {
                    imageView.load(state.imageUrl)
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun FragmentCreateImageBinding.setupListeners() {
        nameEditText.addTextChangedListener { binding.nameEditText.error = null }
        createImageButton.setOnClickListener {
            viewModel.createImage(
                name = nameEditText.text.toString(),
                description = descriptionEditText.text.toString(),
            )
        }
        imageView.setOnClickListener { getContent.launch("image/*") }
    }
}

