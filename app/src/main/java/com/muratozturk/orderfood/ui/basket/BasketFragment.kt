package com.muratozturk.orderfood.ui.basket

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.data.models.ProductsBasketRoomModel
import com.muratozturk.orderfood.data.repo.Repository
import com.muratozturk.orderfood.databinding.FragmentBasketBinding
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding


class BasketFragment : Fragment(R.layout.fragment_basket) {

    private val binding by viewBinding(FragmentBasketBinding::bind)
    private val viewModel by lazy { BasketViewModel(requireContext()) }
    private var basketAmount: Double = 0.0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            resources.getString(R.string.basket)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)


        binding.apply {
            viewModel.apply {
                getBasket()
                initObservers()
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
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initObservers() {
        binding.apply {
            viewModel.apply {
                isLoading.observe(viewLifecycleOwner) {
                    when (it!!) {
                        Repository.LOADING.LOADING -> {
                            shimmerLayout.startShimmer()
                        }
                        Repository.LOADING.DONE -> {
                            shimmerLayout.apply {

                                visibility = View.GONE
                                stopShimmer()
                            }
                            basketRecyclerView.visibility = View.VISIBLE
                        }

                        Repository.LOADING.ERROR -> {
                            shimmerLayout.apply {
                                stopShimmer()
                                visibility = View.GONE
                            }
                            basketRecyclerView.visibility = View.VISIBLE
                        }
                    }

                }

                basketTotalAmount.observe(viewLifecycleOwner) { basketTotalAmount ->
                    if (basketTotalAmount > 0.00) {
                        totalAmount.text =
                            String.format("%.2f", basketTotalAmount) + " ₺"
                        approveLayout.visibility = View.VISIBLE
                        basketRecyclerView.visibility = View.VISIBLE
                        emptyLayout.visibility = View.GONE
                    } else {
                        approveLayout.visibility = View.GONE
                        basketRecyclerView.visibility = View.GONE
                        emptyLayout.visibility = View.VISIBLE
                    }

                    basketAmount = basketTotalAmount

                }

                basketList.observe(viewLifecycleOwner) { basketList ->
                    val basketAdapter =
                        BasketAdapter(basketList as ArrayList<ProductsBasketRoomModel>)
                    basketRecyclerView.adapter = basketAdapter
                    basketAdapter.onDeleteClick = ::onDeleteClick


                    if (basketList.isNotEmpty()) {
                        approveLayout.visibility = View.VISIBLE
                    } else {
                        approveLayout.visibility = View.GONE
                    }

                }
                basketRecyclerView.layoutManager = LinearLayoutManager(context)
                basketRecyclerView.setHasFixedSize(true)

                addOrder.setOnClickListener { findNavController().navigate(BasketFragmentDirections.actionBasketFragmentToCategoriesFragment()) }
                approveBasket.setOnClickListener {
                    findNavController().navigate(
                        BasketFragmentDirections.actionBasketFragmentToPaymentFragment(basketAmount.toFloat())
                    )
                }
            }
        }
    }

    private fun onDeleteClick(productId: Int) {
        viewModel.deleteProductFromBasket(productId)
    }
}