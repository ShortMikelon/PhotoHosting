package kz.aues.photohosting.presentations.main.comments

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentCommentsBinding
import kz.aues.photohosting.utlis.resultObserve
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.BaseScreen
import kz.samsungcampus.android.args
import kz.samsungcampus.android.viewModelCreator
import javax.inject.Inject

@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.fragment_comments) {

    private val binding by viewBinding<FragmentCommentsBinding>()

    @Inject
    lateinit var viewModelFactory: CommentsViewModel.Factory

    private val viewModel by viewModelCreator { viewModelFactory.create(args()) }

    override fun onStart() {
        super.onStart()

        val adapter = CommentsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        resultObserve(
            viewModel.comments,
            onSuccess = {
                binding.progressContainer.root.isVisible = false
                adapter.submitList(it)
                if (it.isEmpty()) {
                    binding.recyclerView.isVisible = false
                    binding.emptyListTextView.isVisible = true
                } else {
                    binding.recyclerView.isVisible = true
                    binding.emptyListTextView.isVisible = false
                }
            }, onPending = {
                binding.progressContainer.root.isVisible = true
                binding.recyclerView.isVisible = false
                binding.emptyListTextView.isVisible = false
            }
        )
    }

    class Screen(
        val imageId: String
    ) : BaseScreen
}

