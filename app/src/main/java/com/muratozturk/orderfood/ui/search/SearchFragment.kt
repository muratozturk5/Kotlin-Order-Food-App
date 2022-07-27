package com.muratozturk.orderfood.ui.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.gone
import com.muratozturk.orderfood.common.visible
import com.muratozturk.orderfood.databinding.FragmentSearchBinding
import com.muratozturk.orderfood.data.models.Product
import com.muratozturk.orderfood.data.repo.Repository
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SearchFragment : Fragment(R.layout.fragment_search) {


    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by lazy { SearchViewModel(requireContext()) }
    private val productsAdapter by lazy { SearchAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.search_product)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        val tryAgain: Button = requireView().findViewById<View>(R.id.tryAgain) as Button


        binding.apply {
            viewModel.apply {
                getSearch()
                initObservers()
                tryAgain.setOnClickListener {
                    getSearch()
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
                item.isVisible = true

                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.imeOptions = EditorInfo.IME_ACTION_DONE
                searchView.queryHint = activity!!.getString(R.string.search) + "..."
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        productsAdapter.filter.filter(newText)


                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initObservers() {
        binding.apply {
            viewModel.apply {
                val errorLayout: LinearLayout =
                    requireView().findViewById(R.id.errorLayout)
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
                    productsRecyclerView.adapter = productsAdapter.also { adapter ->
                        adapter.updateList(products)

                        adapter.onClick = ::clickProduct
                    }
                }
                productsRecyclerView.layoutManager = LinearLayoutManager(context)
                productsRecyclerView.setHasFixedSize(true)
            }
        }
    }

    private fun clickProduct(product: Product) {
        val productNavigation =
            SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(product)

        findNavController().navigate(productNavigation)

    }

}