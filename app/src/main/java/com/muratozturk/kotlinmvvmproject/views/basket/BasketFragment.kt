package com.muratozturk.kotlinmvvmproject.views.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.FragmentBasketBinding
import com.muratozturk.kotlinmvvmproject.views.category.CategoriesViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val viewModel by lazy { CategoriesViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.basket)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }
}