package com.muratozturk.orderfood.ui.login.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.muratozturk.orderfood.MainActivity
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.showCustomToast
import com.muratozturk.orderfood.data.repo.Repository
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

                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        UserRepository.LOADING.LOADING -> {

                        }
                        UserRepository.LOADING.DONE -> {

                        }

                        UserRepository.LOADING.ERROR -> {

                        }
                    }

                }

                isSignIn.observe(viewLifecycleOwner) {
                    if (it) {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
//                        Toast(requireContext()).showCustomToast(
//                            getString(R.string.wrong_email_password),
//                            requireActivity()
//                        )

                        MotionToast.darkToast(
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

                btnSignIn.setOnClickListener {
                    signIn(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                }
            }

        }

    }
}