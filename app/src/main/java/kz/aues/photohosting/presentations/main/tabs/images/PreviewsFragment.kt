package kz.aues.photohosting.presentations.main.tabs.images

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentImagesBinding
import kz.aues.photohosting.domain.main.tabs.previews.entities.PreviewEntity
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.common.Resource

@AndroidEntryPoint
class PreviewsFragment : Fragment(R.layout.fragment_images) {

    private val binding by viewBinding<FragmentImagesBinding>()

    private val viewModel by viewModels<PreviewsViewModel>()

    private lateinit var adapter: PreviewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()
        observePreviews()
    }

    private fun initRecyclerview() {
        adapter = PreviewsAdapter(
            onPreviewClicked = { viewModel.launchImageDetails(it) }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observePreviews() {
        viewModel.preview
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        showSuccessResult(resource.value)
                    }
                    is Resource.Error -> {
                        showErrorResult()
                    }
                    else -> {
                        showProgressbar()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showSuccessResult(list: List<PreviewEntity>) {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.recyclerView.visibility = View.VISIBLE
        adapter.submitList(list)
    }

    private fun showErrorResult() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.tryAgainContainer.root.visibility = View.GONE
    }

    private fun showProgressbar() {
        binding.root.children.forEach { it.visibility = View.GONE }
        binding.progressContainer.root.visibility = View.VISIBLE
    }
}
