package com.muratozturk.orderfood.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.click_shrink_effect.applyClickShrink
import com.muratozturk.orderfood.R

import com.muratozturk.orderfood.common.loadImage
import com.muratozturk.orderfood.common.formatPrice
import com.muratozturk.orderfood.databinding.ProductCardBinding
import com.muratozturk.orderfood.data.models.Product

class ProductsAdapter(private var productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val productCardBinding: ProductCardBinding) :
        RecyclerView.ViewHolder(productCardBinding.root)

    var onClick: (Product) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val productCardBinding = ProductCardBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(productCardBinding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productList[position]

        holder.productCardBinding.apply {
            productText.text = product.name
            productPrice.text = product.price?.formatPrice()
            productImageView.loadImage(product.imagePath.toString())
            root.applyClickShrink()

            root.setOnClickListener { onClick(product) }
        }


        holder.productCardBinding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recyclerview_anim)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}