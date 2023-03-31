package com.google.codelabs.mdc.kotlin.shrine

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.codelabs.mdc.kotlin.shrine.databinding.ShrLoginFragmentBinding

/**
 * Fragment representing the login screen for Shrine.
 */
class LoginFragment : Fragment() {
    private lateinit var binding: ShrLoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = ShrLoginFragmentBinding.inflate(inflater, container, false)

        // Snippet from "Navigate to the next Fragment" section goes here.

        with(binding) {
            nextButton.setOnClickListener {
                if (!isPasswordValid(passwordEditText.text!!)) {
                    passwordTextInput.error = getString(R.string.shr_error_password)
                } else {
                    // Clear the error.
                    passwordTextInput.error = null
                    (activity as NavigationHost).navigateTo(ProductGridFragment(),false)
                }
            }

            passwordEditText.setOnKeyListener { _, _, _ ->
                if (isPasswordValid(passwordEditText.text!!)) {
                    // Clear the error.
                    passwordTextInput.error = null
                }
                false
            }
        }
        return binding.root
    }

    private fun isPasswordValid(text: Editable?): Boolean {
        return text != null && text.length >= resources.getInteger(R.integer.min_password_length)
    }


}
