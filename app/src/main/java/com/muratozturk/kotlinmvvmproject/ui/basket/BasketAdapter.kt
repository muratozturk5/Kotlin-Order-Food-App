package com.muratozturk.kotlinmvvmproject.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.databinding.BasketItemBinding

class BasketAdapter(private var basketList: ArrayList<ProductsBasketRoomModel>) :
    RecyclerView.Adapter<BasketAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val productCardBinding: BasketItemBinding) :
        RecyclerView.ViewHolder(productCardBinding.root)

    var onDeleteClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val basketItemBinding = BasketItemBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(basketItemBinding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = basketList[position]

        holder.productCardBinding.apply {
            productText.text = product.productName
            productCount.text = product.productCount.toString() + " X"
            productPrice.text = String.format("%.2f", product.productPrice) + " ₺"

            deleteBasketProduct.setOnClickListener {
                onDeleteClick(product.productId)
                basketList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(
                    position,
                    itemCount
                )
            }
        }


        holder.productCardBinding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recyclerview_anim)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }
}