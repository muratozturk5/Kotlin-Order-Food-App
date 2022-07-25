package com.muratozturk.kotlinmvvmproject.ui.payment

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.muratozturk.kotlinmvvmproject.R
import com.squareup.picasso.Picasso

class SpinnerAdapter(
    var context: Context,
    var imagesList: ArrayList<Int>,
    var namesList: ArrayList<String>
) :
    BaseAdapter() {
    var inflter: LayoutInflater
    override fun getCount(): Int {
        return imagesList.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        view = inflter.inflate(R.layout.custom_spinner_items, null)
        val icon = view.findViewById<View>(R.id.imageView) as ImageView
        val names = view.findViewById<View>(R.id.textView) as TextView

        icon.setImageResource(imagesList[position])
        names.text = namesList[position]
        return view
    }

    init {
        inflter = LayoutInflater.from(context)
    }
}