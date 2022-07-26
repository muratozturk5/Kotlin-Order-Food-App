package com.muratozturk.orderfood.ui.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.muratozturk.orderfood.R

class SpinnerAdapter(
    var context: Context,
    var imagesList: ArrayList<Int>,
    var namesList: ArrayList<String>
) :
    BaseAdapter() {
    var inflter: LayoutInflater = LayoutInflater.from(context)
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
        val view: View? = inflter.inflate(R.layout.custom_spinner_items, null)
        val icon = view?.findViewById<View>(R.id.imageView) as ImageView
        val names = view.findViewById<View>(R.id.textView) as TextView

        icon.setImageResource(imagesList[position])
        names.text = namesList[position]
        return view
    }

}