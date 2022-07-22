package com.muratozturk.kotlinmvvmproject.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.common.applyClickShrink
import com.muratozturk.kotlinmvvmproject.databinding.ProductCardBinding
import com.muratozturk.kotlinmvvmproject.data.models.Product
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.databinding.BasketItemBinding
import com.squareup.picasso.Picasso

class BasketAdapter(private var basketList: ArrayList<ProductsBasketRoomModel>) :
    RecyclerView.Adapter<BasketAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val productCardBinding: BasketItemBinding) :
        RecyclerView.ViewHolder(productCardBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val basketItemBinding = BasketItemBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(basketItemBinding)
    }

    override fun onBindViewHolder(holder: BasketAdapter.ProductsViewHolder, position: Int) {
        val product = basketList[position]

        holder.productCardBinding.apply {
            productText.text = product.productName
            productCount.text = product.productCount.toString() +"X"
            productPrice.text = String.format("%.2f", product.productPrice) + " ₺"


        }


        holder.productCardBinding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recyclerview_anim)
    }

    override fun getItemCount(): Int {
        return basketList.size
    }
}