package com.muratozturk.kotlinmvvmproject.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.kotlinmvvmproject.R
import com.muratozturk.kotlinmvvmproject.databinding.CategoryCardBinding
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.services.applyClickShrink
import com.squareup.picasso.Picasso

class CategoriesAdapter(private var kitaplarList: ArrayList<Categories>) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryCardDesign>() {

    var onClick: (Categories) -> Unit = {}

    class CategoryCardDesign(val categoryCardBinding: CategoryCardBinding) :
        RecyclerView.ViewHolder(categoryCardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryCardDesign {
        val layoutInflater = LayoutInflater.from(parent.context)
        val categoryCardBinding = CategoryCardBinding.inflate(layoutInflater, parent, false)
        return CategoryCardDesign(categoryCardBinding)
    }

    override fun onBindViewHolder(holder: CategoryCardDesign, position: Int) {
        val kitap = kitaplarList[position]

        holder.categoryCardBinding.apply {
            categoryText.text = kitap.name
            Picasso.get()
                .load("https://liwapos.com/samba.mobil/Content/" + kitap.imagePath.substring(39))
                .error(R.drawable.error)
                .placeholder(R.drawable.loading).into(categoryImageView)
            root.applyClickShrink()

            root.setOnClickListener {
                onClick(kitap)
            }
        }
    }

    override fun getItemCount(): Int {
        return kitaplarList.size
    }


}