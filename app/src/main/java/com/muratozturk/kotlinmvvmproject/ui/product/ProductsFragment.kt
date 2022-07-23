package com.muratozturk.kotlinmvvmproject.ui.product

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
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
import com.muratozturk.kotlinmvvmproject.data.models.Product
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ProductsFragment : Fragment(R.layout.fragment_products) {


    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val viewModel by lazy { ProductsViewModel(requireContext()) }


    private val args: ProductsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.category

        val tryAgain: Button = requireActivity().findViewById<View>(R.id.tryAgain) as Button


        (activity as AppCompatActivity?)!!.supportActionBar!!.title = category.name
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.apply {
            viewModel.apply {

                getProducts(category.id)
                initObservers()
                tryAgain.setOnClickListener {
                    getProducts(category.id)
                }
            }
        }

        initToolbarMenu()
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

    private fun initObservers() {
        binding.apply {
            viewModel.apply {
                val errorLayout: LinearLayout =
                    requireView().findViewById<View>(R.id.errorLayout) as LinearLayout
                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        Repository.LOADING.LOADING -> {
                            shimmerLayout.apply {
                                startShimmer()
                                visibility = View.VISIBLE
                            }
                            productsRecyclerView.visibility = View.GONE
                            errorLayout.visibility = View.GONE
                        }
                        Repository.LOADING.DONE -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            productsRecyclerView.visibility = View.VISIBLE
                            errorLayout.visibility = View.GONE
                        }

                        Repository.LOADING.ERROR -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            productsRecyclerView.visibility = View.GONE
                            errorLayout.visibility = View.VISIBLE

                        }
                    }

                }


                productList.observe(viewLifecycleOwner) { products ->
                    val productsAdapter = ProductsAdapter(products as ArrayList<Product>)
                    productsRecyclerView.adapter = productsAdapter
                    productsAdapter.onClick = ::clickProduct

                }
                productsRecyclerView.layoutManager = LinearLayoutManager(context)
                productsRecyclerView.setHasFixedSize(true)
            }
        }
    }

    private fun clickProduct(product: Product) {
        val productNavigation =
            ProductsFragmentDirections.actionProductsFragmentToProductDetailFragment(product)

        findNavController().navigate(productNavigation)

    }
}