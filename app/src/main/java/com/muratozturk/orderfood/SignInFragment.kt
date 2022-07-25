package com.muratozturk.orderfood

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muratozturk.orderfood.databinding.FragmentProfileBinding
import com.muratozturk.orderfood.databinding.FragmentSignInBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by lazy { SignInViewModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.apply {
                isSignIn.observe(viewLifecycleOwner) {
                    if (it) {
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
//                        showSnackbar(view, R.string.wrong_email_password)
                    }
                }
            }

        }

    }
}