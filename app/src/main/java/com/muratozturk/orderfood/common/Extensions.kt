package com.muratozturk.orderfood.common


import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.muratozturk.orderfood.R
import com.squareup.picasso.Picasso


fun Double.formatPrice(): String {
    return String.format("%.2f", this) + " ₺"
}

fun Float.formatPrice(): String {
    return String.format("%.2f", this) + " ₺"
}

fun ImageView.loadImage(url: String) {
    Picasso.get()
        .load("https://liwapos.com/samba.mobil/Content/$url")
        .placeholder(R.drawable.loading)
        .error(R.drawable.error)
        .into(this)
}

fun getPaymentImages(): ArrayList<Int> {
    return arrayListOf(R.drawable.ic_cash, R.drawable.ic_bank_cards)
}

fun Context.getPaymentNames(): ArrayList<String> {
    return arrayListOf(this.getString(R.string.cash), this.getString(R.string.credit_card))
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Toast.showCustomToast(toastText: String, activity: Activity) {

    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast,
        activity.findViewById(R.id.toast_layout_root)
    )

    val text =
        layout.findViewById<View>(R.id.text) as TextView
    text.text = toastText

    this.apply {
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }


}