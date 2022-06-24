package com.muratozturk.kotlinmvvmproject.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.ProductCardBinding
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.services.applyClickShrink
import com.squareup.picasso.Picasso

class ProductsAdapter(private var productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {

    class ProductsViewHolder(val productCardBinding: ProductCardBinding) :
        RecyclerView.ViewHolder(productCardBinding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val productCardBinding = ProductCardBinding.inflate(layoutInflater, parent, false)
        return ProductsViewHolder(productCardBinding)
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductsViewHolder, position: Int) {
        val product = productList[position]

        holder.productCardBinding.apply {
            productText.text = product.name
            Picasso.get()
                .load("https://liwapos.com/samba.mobil/Content/" + product.imagePath.substring(39))
                .error(R.drawable.error)
                .placeholder(R.drawable.loading).into(productImageView)
            root.applyClickShrink()

        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}