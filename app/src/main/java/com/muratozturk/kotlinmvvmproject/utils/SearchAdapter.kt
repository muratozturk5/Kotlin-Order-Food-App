package com.muratozturk.kotlinmvvmproject.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.ProductCardBinding
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.services.applyClickShrink
import com.squareup.picasso.Picasso

class SearchAdapter() :
    RecyclerView.Adapter<SearchAdapter.ProductsViewHolder>(), Filterable {

    var onClick: (Product) -> Unit = {}
    private var productList = ArrayList<Product>()
    var productFilterList = ArrayList<Product>()

    init {
        productFilterList = productList
    }

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

    override fun onBindViewHolder(holder: SearchAdapter.ProductsViewHolder, position: Int) {
        val product = productFilterList[position]

        holder.productCardBinding.apply {
            productText.text = product.name
            productPrice.text = String.format("%.2f", product.price) + " ₺"
            Picasso.get()
                .load("https://liwapos.com/samba.mobil/Content/" + product.imagePath?.substring(39))
                .error(R.drawable.error)
                .placeholder(R.drawable.loading).into(productImageView)
            root.applyClickShrink()

            root.setOnClickListener { onClick(product) }
        }

        holder.productCardBinding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recyclerview_anim)
    }

    fun updateList(list: List<Product>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return productFilterList.size
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val searchText = constraint.toString().lowercase()
                productFilterList = if (searchText.isEmpty()) {
                    productList
                } else {
                    val resultList = ArrayList<Product>()
                    for (row in productList) {
                        row.name.let { productName ->

                            if (productName!!.lowercase().contains(searchText)) {
                                resultList.add(row)
                            }
                        }

                    }
                    resultList
                }

                val filterResults = FilterResults()
                filterResults.values = productFilterList

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                productFilterList = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }
}