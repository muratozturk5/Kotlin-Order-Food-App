package com.muratozturk.kotlinmvvmproject.ui.basket

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.data.models.Product
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import com.muratozturk.kotlinmvvmproject.databinding.FragmentBasketBinding
import com.muratozturk.kotlinmvvmproject.ui.category.CategoriesViewModel
import com.muratozturk.kotlinmvvmproject.utils.BasketAdapter
import com.muratozturk.kotlinmvvmproject.utils.ProductsAdapter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val viewModel by lazy { BasketViewModel(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.basket)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)


        binding.apply {
            viewModel.apply {

                getBasket()
                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        Repository.LOADING.LOADING -> {
                            shimmerLayout.startShimmer()
                        }
                        Repository.LOADING.DONE -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            basketRecyclerView.visibility = View.VISIBLE
                        }

                        Repository.LOADING.ERROR -> {
                            binding.shimmerLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            basketRecyclerView.visibility = View.VISIBLE
                        }
                    }

                }


                basketList.observe(viewLifecycleOwner) { basketList ->
                    val basketAdapter =
                        BasketAdapter(basketList as ArrayList<ProductsBasketRoomModel>)
                    basketRecyclerView.adapter = basketAdapter

                }
                basketRecyclerView.layoutManager = LinearLayoutManager(context)
                basketRecyclerView.setHasFixedSize(true)
            }

        }

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