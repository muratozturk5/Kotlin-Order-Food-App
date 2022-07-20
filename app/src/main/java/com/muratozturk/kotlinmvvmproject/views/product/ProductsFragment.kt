package com.muratozturk.kotlinmvvmproject.views.product

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.FragmentProductsBinding
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository
import com.muratozturk.kotlinmvvmproject.utils.ProductsAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ProductsFragment : Fragment(R.layout.fragment_products) {


    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val viewModel by lazy { ProductsViewModel() }


    private val args: ProductsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.category

        viewModel.getProducts(category.id)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = category.name
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}