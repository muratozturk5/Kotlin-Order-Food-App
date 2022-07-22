package com.muratozturk.kotlinmvvmproject.ui.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.databinding.FragmentProductDetailBinding
import com.muratozturk.kotlinmvvmproject.ui.category.CategoriesViewModel
import com.squareup.picasso.Picasso


class ProductDetailFragment : BottomSheetDialogFragment() {

    private val viewModel by lazy { ProductDetailViewModel(requireContext()) }
    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private val args: ProductDetailFragmentArgs by navArgs()
    private var count: Int = 1
    private var totalPrice: Double? = null
    private var price: Double? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product

        binding.apply {
            Picasso.get()
                .load("https://liwapos.com/samba.mobil/Content/" + product.imagePath?.substring(39))
                .into(productImage)

            totalPrice = product.price
            price = product.price
            productName.text = product.name
            updatePrice()
            productCount.text = count.toString()
            increase.setOnClickListener { increaseCount() }
            decrease.setOnClickListener { decreaseCount() }
            cancel.setOnClickListener { findNavController().popBackStack() }
            addToBasket.setOnClickListener {
                viewModel.addBookToBasket(
                    ProductsBasketRoomModel(
                        productId = product.menuItemId!!,
                        productName = product.name,
                        productCount = count,
                        productPrice = totalPrice,
                        productImage = product.imagePath
                    )
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun updatePrice() {
        totalPrice = count * price!!
        binding.productPrice.text = String.format("%.2f", totalPrice) + " ₺"
    }

    private fun increaseCount() {
        count += 1
        binding.productCount.text = count.toString()
        updatePrice()
    }

    private fun decreaseCount() {
        if (count >= 2) {
            count -= 1
            binding.productCount.text = count.toString()
            updatePrice()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}