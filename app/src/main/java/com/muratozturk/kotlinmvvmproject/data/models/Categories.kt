package com.muratozturk.kotlinmvvmproject.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Categories(
    @SerializedName("Id") @Expose var id: Int,
    @SerializedName("Name") @Expose var name: String,
    @SerializedName("ImagePath") @Expose var imagePath: String
):Serializable