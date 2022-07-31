package com.muratozturk.orderfood.ui.productdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.loadImage
import com.muratozturk.orderfood.common.formatPrice
import com.muratozturk.orderfood.common.showCustomToast
import com.muratozturk.orderfood.data.models.ProductsBasketRoomModel
import com.muratozturk.orderfood.databinding.FragmentProductDetailBinding


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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val product = args.product

        binding.apply {
            productImage.loadImage(product.imagePath.toString())
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
                        productId = product.id!!,
                        productName = product.name,
                        productCount = count,
                        productPrice = totalPrice,
                        productImage = product.imagePath
                    )
                )
                Toast(requireContext()).showCustomToast(
                    getString(R.string.success_add_basket),
                    requireActivity()
                )
                findNavController().popBackStack()
            }
        }
    }

    private fun updatePrice() {
        totalPrice = count * price!!
        binding.productPrice.text = totalPrice!!.formatPrice()
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