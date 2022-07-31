package com.muratozturk.orderfood.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("id") @Expose var id: Int?,
    @SerializedName("name") @Expose var name: String?,
    @SerializedName("imagePath") @Expose var imagePath: String?,
    @SerializedName("price") @Expose var price: Double?,
    @SerializedName("portions") @Expose var portion: String?
):Serializable