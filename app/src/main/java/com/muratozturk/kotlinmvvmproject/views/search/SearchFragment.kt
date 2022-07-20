package com.muratozturk.kotlinmvvmproject.views.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.FragmentSearchBinding
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository
import com.muratozturk.kotlinmvvmproject.utils.ProductsAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SearchFragment : Fragment(R.layout.fragment_search) {


    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by lazy { SearchViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.search)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        viewModel.getSearch()

        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it!!) {
                Repository.LOADING.LOADING -> {
                    binding.shimmerLayout.startShimmer()
                }
                Repository.LOADING.DONE -> {
                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    binding.productsRecyclerView.visibility = View.VISIBLE
                }

                Repository.LOADING.ERROR -> {
                    binding.shimmerLayout.apply {
                        stopShimmer()
                        visibility = View.GONE
                    }
                    binding.productsRecyclerView.visibility = View.VISIBLE
                }
            }

        }


        viewModel.productList.observe(viewLifecycleOwner) { products ->
            val productsAdapter = ProductsAdapter(products as ArrayList<Product>)
            binding.productsRecyclerView.adapter = productsAdapter

        }
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productsRecyclerView.setHasFixedSize(true)

    }


}