package com.muratozturk.orderfood.ui.login.signin


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.muratozturk.orderfood.ui.MainActivity
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.data.repo.UserRepository
import com.muratozturk.orderfood.databinding.FragmentSignInBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by lazy { SignInViewModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.apply {

                initObservers()
                btnSignIn.setOnClickListener {
                    signIn(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                }
            }

        }

    }

    private fun initObservers() {
        binding.apply {
            viewModel.apply {
                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        UserRepository.LOADING.LOADING -> {
                            btnSignIn.startAnimation()
                        }
                        UserRepository.LOADING.DONE -> {

                            btnSignIn.revertAnimation {
                                btnSignIn.setBackgroundResource(R.drawable.rounded_bg3)
                            }
                        }

                        UserRepository.LOADING.ERROR -> {
                            btnSignIn.revertAnimation {
                                btnSignIn.setBackgroundResource(R.drawable.rounded_bg3)
                            }
                        }
                    }

                }

                isSignIn.observe(viewLifecycleOwner) {
                    if (it) {
                        MotionToast.createColorToast(
                            requireActivity(),
                            getString(R.string.success),
                            getString(R.string.login_success),
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), R.font.circular)
                        )

                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        MotionToast.createColorToast(
                            requireActivity(),
                            getString(R.string.error),
                            getString(R.string.wrong_email_password),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), R.font.circular)
                        )
                    }

                }

                isValidMail.observe(viewLifecycleOwner) {
                    if (it.not()) {
                        emailInputLayout.error = getString(R.string.invalid_mail)
                    } else {
                        emailInputLayout.error = ""
                    }
                }

                isInfosValid.observe(viewLifecycleOwner) {
                    if (it.not())

                        MotionToast.createColorToast(
                            requireActivity(),
                            getString(R.string.error),
                            getString(R.string.complate_not_entered_info),
                            MotionToastStyle.ERROR,
                            MotionToast.GRAVITY_TOP or MotionToast.GRAVITY_CENTER,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(requireContext(), R.font.circular)
                        )

                }
            }
        }
    }

}