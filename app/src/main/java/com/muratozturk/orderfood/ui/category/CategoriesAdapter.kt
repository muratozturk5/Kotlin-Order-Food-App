package com.muratozturk.orderfood.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.muratozturk.orderfood.R
import com.muratozturk.orderfood.databinding.CategoryCardBinding
import com.muratozturk.orderfood.data.models.Categories
import com.muratozturk.orderfood.common.applyClickShrink
import com.squareup.picasso.Picasso

class CategoriesAdapter(private var categoriesList: ArrayList<Categories>) :
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
        val category = categoriesList[position]

        holder.categoryCardBinding.apply {
            categoryText.text = category.name
            Picasso.get()
                .load("https://liwapos.com/samba.mobil/Content/" + category.imagePath.substring(39))
                .error(R.drawable.error)
                .placeholder(R.drawable.loading).into(categoryImageView)
            root.applyClickShrink()

            root.setOnClickListener {
                onClick(category)
            }
        }


        holder.categoryCardBinding.root.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recyclerview_anim)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}