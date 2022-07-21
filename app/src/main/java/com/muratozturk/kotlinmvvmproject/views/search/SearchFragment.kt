package com.muratozturk.kotlinmvvmproject.views.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.FragmentSearchBinding
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository
import com.muratozturk.kotlinmvvmproject.utils.ProductsAdapter
import com.muratozturk.kotlinmvvmproject.utils.SearchAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class SearchFragment : Fragment(R.layout.fragment_search) {


    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by lazy { SearchViewModel() }
    private val productsAdapter by lazy { SearchAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.search_product)
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
            binding.productsRecyclerView.adapter = productsAdapter.also { adapter ->
                adapter.updateList(products)

            }
        }
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.productsRecyclerView.setHasFixedSize(true)


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
                        Log.e("onQueryTextChange", newText)
//                if (adapter != null) {
//                    if (adapter.getFilter() != null) {
//                        adapter.getFilter().filter(newText)
//                    }
//                }

                        productsAdapter.filter.filter(newText)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return false
//                return when (menuItem.itemId) {
//                    R.id.menu_clear -> {
//                        // clearCompletedTasks()
//                        true
//                    }
//                    R.id.menu_refresh -> {
//                        // loadTasks(true)
//                        true
//                    }
//                    else -> false
//                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}