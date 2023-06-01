package kz.aues.photohosting.presentations.main.sign_in

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kz.aues.photohosting.R
import kz.aues.photohosting.databinding.FragmentSignInBinding
import kz.aues.photohosting.domain.main.sign_in.entities.SignInEntity
import kz.aues.photohosting.domain.main.sign_in.entities.SignInField
import kz.aues.photohosting.utlis.viewBinding
import kz.samsungcampus.android.observeEvent

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val viewModel by viewModels<SignInViewModel>()

    private val binding by viewBinding<FragmentSignInBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEvents()
        with(binding) {
            setupListeners()
            observeState()
        }
    }

    private fun observeEvents() {
        viewModel.focusFieldLiveEvent.observeEvent(viewLifecycleOwner) { field ->
            getEditTextByField(field).requestFocus()
            getEditTextByField(field).selectAll()
        }
        viewModel.clearPasswordFieldLiveEvent.observeEvent(viewLifecycleOwner) {
            binding.passwordEditText.requestFocus()
            binding.passwordEditText.text.clear()
        }
        viewModel.snackbarLiveEvent.observeEvent(viewLifecycleOwner) {
            Snackbar.make(binding.root, it.text, it.duration)
                .setTextColor(ContextCompat.getColor(requireContext(), it.textColorId))
                .setBackgroundTint(ContextCompat.getColor(requireContext(), it.backgroundColorId))
                .show()
        }
    }

    private fun FragmentSignInBinding.setupListeners() {
        signUpButton.setOnClickListener {
            viewModel.signUp()
        }
        signInButton.setOnClickListener {
            val signInEntity = SignInEntity(
                email = emailEditText.text.toString().trim(),
                password = passwordEditText.text.toString().trim()
            )
            viewModel.signIn(signInEntity)
        }
        emailEditText.addTextChangedListener { viewModel.clearError(SignInField.EMAIL) }
        passwordEditText.addTextChangedListener { viewModel.clearError(SignInField.PASSWORD) }
    }

    private fun FragmentSignInBinding.observeState() {
        viewModel.stateFlow.flowWithLifecycle(lifecycle)
            .onEach { state ->
                progressBar.isVisible = state.showProgress
                signUpButton.isEnabled = state.enableSignInButton
                signInButton.isEnabled = state.enableSignInButton

                cleanUpErrors()
                if (state.fieldError != null) {
                    val editText = getEditTextByField(state.fieldError.first)
                    editText.error = state.fieldError.second
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun FragmentSignInBinding.cleanUpErrors() {
        emailEditText.error = null
        passwordEditText.error = null
    }

    private fun getEditTextByField(field: SignInField): EditText {
        return when (field) {
            SignInField.EMAIL -> binding.emailEditText
            SignInField.PASSWORD -> binding.passwordEditText
        }
    }
}

