package kz.aues.photohosting.presentations.main.image_details

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentImageDetailsBinding
import kz.aues.photohosting.utlis.resultObserve
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.BaseScreen
import kz.samsungcampus.android.args
import kz.samsungcampus.android.viewModelCreator
import kz.samsungcampus.common.extensions.getYearMonthDay
import javax.inject.Inject

@AndroidEntryPoint
class ImageDetailsFragment : Fragment(R.layout.fragment_image_details) {

    private val binding by viewBinding<FragmentImageDetailsBinding>()

    @Inject
    lateinit var factory: ImageDetailsViewModel.Factory

    private val viewModel by viewModelCreator { factory.create(args()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            observePhoto()
            setupListeners()
        }
    }

    private fun FragmentImageDetailsBinding.setupListeners() {
        writeCommentEditText.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.launchAddCommentDialog()
                writeCommentEditText.clearFocus()
            }
        }
        showCommentButton.setOnClickListener { viewModel.launchCommentsFragment() }
    }

    private fun FragmentImageDetailsBinding.observePhoto() {
        viewModel.image.resultObserve(
            lifecycle = viewLifecycleOwner.lifecycle,
            scope = viewLifecycleOwner.lifecycleScope,
            onSuccess = { state ->
                root.children.forEach { it.isVisible = true }
                errorContainer.root.isVisible = false
                pendingContainer.root.isVisible = false
                nameTextView.text = state.name
                authorTextView.text = getString(R.string.author_name, state.author)
                descriptionTextView.text = state.description
                imageView.load(state.path)
                createdAtTextView.text = state.createdAt.getYearMonthDay()

                if (state.countComments == 0) showCommentButton.isVisible = false
                else {
                    showCommentButton.isVisible = true
                    showCommentButton.text = getString(R.string.show_comments, state.countComments)
                }
            }, onError = {
                root.children.forEach { it.isVisible = false }
                errorContainer.root.isVisible = true
                pendingContainer.root.isVisible = false

            }, onPending = {
                root.children.forEach { it.isVisible = false }
                errorContainer.root.isVisible = false
                pendingContainer.root.isVisible = true
            }
        )
    }

    class Screen(
        val id: String
    ) : BaseScreen

}