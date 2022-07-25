package com.muratozturk.kotlinmvvmproject.ui.productdetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.common.showCustomToast
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.databinding.FragmentProductDetailBinding
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
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
//                setupFullHeight(it)
//                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
//                behaviour.skipCollapsed = true
            }
        }
        return dialog
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