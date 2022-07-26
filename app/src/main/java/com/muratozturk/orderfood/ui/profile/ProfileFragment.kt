package com.muratozturk.orderfood.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.muratozturk.orderfood.ui.login.LoginActivity
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.data.repo.UserRepository
import com.muratozturk.orderfood.databinding.FragmentProfileBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by lazy { ProfileViewModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.profile)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        binding.apply {
            viewModel.apply {

                initObservers()
                
                logOut.setOnClickListener {
                    signOut().also {
                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
            }
        }

        initToolbarMenu()
    }

    private fun initObservers() {
        binding.apply {
            viewModel.apply {

                userInfo.observe(viewLifecycleOwner) {
                    email.text = it.email
                    phoneNumber.text = it.phoneNumber
                }

                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        UserRepository.LOADING.LOADING -> {
                            logOut.startAnimation()
                        }
                        UserRepository.LOADING.DONE -> {

                            logOut.revertAnimation {
                                logOut.setBackgroundResource(R.drawable.rounded_bg3)
                            }
                        }

                        UserRepository.LOADING.ERROR -> {
                            logOut.revertAnimation {
                                logOut.setBackgroundResource(R.drawable.rounded_bg3)
                            }
                        }
                    }

                }
            }
        }
    }

    private fun initToolbarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search, menu)
                val item = menu.findItem(R.id.action_search)
                item.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}