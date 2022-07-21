package com.muratozturk.kotlinmvvmproject.ui.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search, menu)
                val item = menu.findItem(R.id.action_search)
                item.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}