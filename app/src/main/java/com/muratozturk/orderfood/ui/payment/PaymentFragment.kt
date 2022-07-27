package com.muratozturk.orderfood.ui.payment


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.common.getPaymentImages
import com.muratozturk.orderfood.common.getPaymentNames
import com.muratozturk.orderfood.common.formatPrice
import com.muratozturk.orderfood.databinding.FragmentPaymentBinding


class PaymentFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy { PaymentViewModel(requireContext()) }

    private val args: PaymentFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPaymentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            closePaymentScreen.setOnClickListener { findNavController().popBackStack() }
            orderNow.setOnClickListener {
                if (orderAddress.text.isEmpty().not()) {
                    showOrderSuccessDialog()
                    viewModel.clearBasket()
                } else {
                    orderAddress.error = getString(R.string.address_error)
                }

            }
            cancel.setOnClickListener { findNavController().popBackStack() }


            val customAdapter =
                SpinnerAdapter(
                    requireContext(),
                    getPaymentImages(),
                    requireContext().getPaymentNames()
                )
            paymentTypes.adapter = customAdapter

            orderAmountPrice.text = args.totalPrice.formatPrice()
            discountAmount.text = (args.totalPrice * 0.20).formatPrice()
            orderPaymentTotalPrice.text =
                ((args.totalPrice) - (args.totalPrice * 0.20)).formatPrice()
        }
    }

    private fun showOrderSuccessDialog() {

        val alertDialog = AlertDialog.Builder(requireContext())
        val factory = LayoutInflater.from(requireContext())
        val view = factory.inflate(R.layout.dialog_order_approved, null)
        alertDialog.setView(view)

        val dialog = alertDialog.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.setCancelable(false)

        val button = view.findViewById<View>(R.id.okay) as Button
        button.setOnClickListener {
            dialog.dismiss()
            dialog.cancel()
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToCategoriesFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}