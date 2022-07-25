package com.muratozturk.orderfood.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("Name") @Expose var name: String?,
    @SerializedName("ImagePath") @Expose var imagePath: String?,
    @SerializedName("Price") @Expose var price: Double?,
    @SerializedName("MenuItemId") @Expose var menuItemId: Int?,
    @SerializedName("GroupName") @Expose var groupName: String?,
    @SerializedName("CustomTags") @Expose var customTags: String?,
    @SerializedName("Expr1") @Expose var portion: String?
):Serializable