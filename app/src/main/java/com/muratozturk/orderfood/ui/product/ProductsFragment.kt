package com.muratozturk.orderfood.ui.product

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
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.gone
import com.muratozturk.orderfood.common.visible
import com.muratozturk.orderfood.databinding.FragmentProductsBinding
import com.muratozturk.orderfood.data.models.Product
import com.muratozturk.orderfood.data.repo.Repository
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class ProductsFragment : Fragment(R.layout.fragment_products) {


    private val binding by viewBinding(FragmentProductsBinding::bind)
    private val viewModel by lazy { ProductsViewModel(requireContext()) }


    private val args: ProductsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.category

        val tryAgain: Button = requireView().findViewById<View>(R.id.tryAgain) as Button


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
                                visible()
                            }
                            productsRecyclerView.gone()
                            errorLayout.gone()
                        }
                        Repository.LOADING.DONE -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                gone()
                            }
                            productsRecyclerView.visible()
                            errorLayout.gone()
                        }

                        Repository.LOADING.ERROR -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                gone()
                            }
                            productsRecyclerView.gone()
                            errorLayout.visible()

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