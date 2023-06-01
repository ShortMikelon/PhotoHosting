package kz.aues.photohosting.presentations.main.sign_up

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentSignUpBinding
import kz.aues.photohosting.domain.main.sign_up.entities.SignUpField
import kz.aues.photohosting.utlis.dpToPx
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.observeEvent

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.processResultGallery(uri)
        }

    private val binding by viewBinding<FragmentSignUpBinding>()

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents(savedInstanceState)
        with(binding) {
            setupListeners()
            observeState()
        }
    }

    private fun observeEvents(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            viewModel.initialEmailFieldLiveData.observeEvent(viewLifecycleOwner) {
                binding.emailEditText.setText(it)
            }
        }
        viewModel.focusFieldLiveData.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).requestFocus()
            getEditTextByField(it).selectAll()
        }
        viewModel.clearFieldLiveData.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).text.clear()
        }
        viewModel.snackbarLiveData.observeEvent(viewLifecycleOwner) {
            Snackbar.make(binding.root, it.text, it.duration)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), it.backgroundColorId))
                .setTextColor(ContextCompat.getColor(requireContext(), it.textColorId))
                .show()
        }
    }

    private fun FragmentSignUpBinding.observeState() {
        viewModel.stateFlow.flowWithLifecycle(lifecycle)
            .onEach { state ->
                avatarImageView.isVisible = state.showAvatar
                if (state.avatarUri != null) {
                    avatarImageView.load(state.avatarUri) {
                        size(
                            width = dpToPx(R.dimen.avatar_size),
                            height = dpToPx(R.dimen.avatar_size)
                        )
                        scale(Scale.FILL)
                    }
                }
                progressBar.isVisible = state.showProgress
                signUpButton.isEnabled = state.enableSignUpButton

                cleanUpErrors()
                if (state.fieldError != null) {
                    val editText = getEditTextByField(state.fieldError.first)
                    editText.error = state.fieldError.second
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun FragmentSignUpBinding.setupListeners() {
        chooseAvatarButton.setOnClickListener {
            getContent.launch("image/*")
        }
        signUpButton.setOnClickListener {
            viewModel.signUp(
                name = nameEditText.text.toString().trim(),
                email = emailEditText.text.toString().trim(),
                password = passwordEditText.text.toString().trim(),
                repeatPassword = repeatPasswordEditText.text.toString().trim(),
            )
        }

        emailEditText.addTextChangedListener { viewModel.clearError(SignUpField.EMAIL) }
        nameEditText.addTextChangedListener { viewModel.clearError(SignUpField.USERNAME) }
        passwordEditText.addTextChangedListener { viewModel.clearError(SignUpField.PASSWORD) }
        repeatPasswordEditText.addTextChangedListener { viewModel.clearError(SignUpField.REPEAT_PASSWORD) }
    }

    private fun FragmentSignUpBinding.cleanUpErrors() {
        emailEditText.error = null
        nameEditText.error = null
        passwordEditText.error = null
        repeatPasswordEditText.error = null
    }

    private fun getEditTextByField(field: SignUpField): EditText {
        return when (field) {
            SignUpField.EMAIL -> binding.emailEditText
            SignUpField.USERNAME -> binding.nameEditText
            SignUpField.PASSWORD -> binding.passwordEditText
            SignUpField.REPEAT_PASSWORD -> binding.repeatPasswordEditText
        }
    }
}