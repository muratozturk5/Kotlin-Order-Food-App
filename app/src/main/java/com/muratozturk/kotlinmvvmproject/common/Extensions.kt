package com.muratozturk.kotlinmvvmproject.common


import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.muratozturk.kotlinmvvmproject.R


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