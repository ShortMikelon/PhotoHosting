package kz.aues.photohosting.presentations.main.add_comments

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.DialogAddCommentBinding
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.BaseScreen
import kz.samsungcampus.android.args
import kz.samsungcampus.android.viewModelCreator
import javax.inject.Inject

@AndroidEntryPoint
class AddCommentDialogFragment : BottomSheetDialogFragment(R.layout.dialog_add_comment) {

    private val binding by viewBinding<DialogAddCommentBinding>()

    @Inject
    lateinit var viewModelFactory: AddCommentViewModel.Factory

    private val viewModel by viewModelCreator { viewModelFactory.create(args()) }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            publishTextView.setOnClickListener {
                root.isClickable = false
                progressBar.isVisible = true
                dialog?.setCancelable(false)
                viewModel.sendComment(commentEditText.text.toString())

                observeSendingStatus()
            }
            closeDialogButton.setOnClickListener { dialog?.dismiss() }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding.commentEditText.requestFocus()
    }

    private fun DialogAddCommentBinding.observeSendingStatus() {
        viewModel.sendingStatus.observe(viewLifecycleOwner) {
            root.isClickable = true
            progressBar.isVisible = false
            if (it) {
                val backgroundColor = ContextCompat.getColor(requireContext(), R.color.gray_100)
                val textColor = ContextCompat.getColor(requireContext(), R.color.black)

                val activityViewRoot = requireActivity().findViewById<View>(R.id.mainActivityRoot)

                Snackbar.make(
                    activityViewRoot,
                    R.string.comment_sent_success,
                    Snackbar.LENGTH_SHORT
                )
                    .setBackgroundTint(backgroundColor)
                    .setTextColor(textColor)
                    .show()

                dialog?.dismiss()
            } else {
                dialog?.setCancelable(true)

                val backgroundColor =
                    ContextCompat.getColor(requireContext(), R.color.warning_background)
                val textColor = ContextCompat.getColor(requireContext(), R.color.white)

                Snackbar.make(root, R.string.error_has_occurred, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(backgroundColor)
                    .setTextColor(textColor)
                    .show()

            }
        }
    }

    class Screen(
        val imageId: String,
        val shouldCommentsUpdated: Boolean
    ) : BaseScreen

}