package com.muratozturk.orderfood.ui.login.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.showCustomToast
import com.muratozturk.orderfood.databinding.FragmentSignUpBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by lazy { SignUpViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel.apply {
                signUpButton.setOnClickListener {
                    signUp(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString(),
                        confirmPasswordEditText.text.toString(),
                        phoneNumberEditText.text.toString()
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
                    if (it.not())

                        MotionToast.darkToast(
                            requireActivity(),
                            getString(R.string.error),
                            getString(R.string.complate_not_entered_info),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), R.font.circular)
                        )

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

                        MotionToast.darkToast(
                            requireActivity(),
                            getString(R.string.success),
                            getString(R.string.sign_up_success),
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), R.font.circular)
                        )

                        clearFields()
                    } else {
                        emailInputLayout.error = getString(R.string.registered_mail)
                    }
                }
            }
        }
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