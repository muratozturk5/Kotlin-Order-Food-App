package com.muratozturk.orderfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muratozturk.orderfood.databinding.FragmentSignInBinding
import com.muratozturk.orderfood.databinding.FragmentSignUpBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by lazy { SignUpViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.apply {
                signUpButton.setOnClickListener {
                    signUp(
                        emailEditText.toString(),
                        passwordEditText.toString(),
                        confirmPasswordEditText.toString(),
                        phoneNumberEditText.toString()
                    )
                }
            }
        }

        initObservers()
    }

    private fun initObservers() {

        with(binding) {

            with(viewModel) {

                isInfosValid.observe(viewLifecycleOwner) {
//                    if (it.not())
//                        showSnackbar(
//                        requireView(),
//                        R.string.incomplete_information_entered
//                    )
                }

                isValidMail.observe(viewLifecycleOwner) {
                    if (it.not()) {
                        emailInputLayout.error = getString(R.string.invalid_mail)
                    } else {
                        emailInputLayout.error = ""
                    }
                }

                isPasswordMatch.observe(viewLifecycleOwner) {
                    if (it.not()) {
                        passwordInputLayout.error = getString(R.string.password_match_error)
                        confirmPasswordInputLayout.error = getString(R.string.password_match_error)
                    } else {
                        passwordInputLayout.error = ""
                        confirmPasswordInputLayout.error = ""
                    }
                }

                isSignUp.observe(viewLifecycleOwner) {
                    if (it) {
//                        showSnackbar(requireView(), R.string.sign_up_snack_text)
                        clearFields()
                    } else {
                        emailInputLayout.error = getString(R.string.registered_mail)
                    }
                }
            }
        }
    }

    fun signUpButton(
        email: String,
        password: String,
        confirmPassword: String,
        nickname: String,
        phoneNumber: String
    ) {
        viewModel.signUp(email, password, confirmPassword, phoneNumber)
    }

    private fun clearFields() {
        with(binding) {
            emailEditText.setText("")
            emailInputLayout.error = ""
            passwordEditText.setText("")
            passwordInputLayout.error = ""
            confirmPasswordEditText.setText("")
            confirmPasswordInputLayout.error = ""
            phoneNumberEditText.setText("")
            phoneNumberInputLayout.error = ""
        }
    }


}