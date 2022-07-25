package com.muratozturk.orderfood.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productsbasketdatabase")
data class ProductsBasketRoomModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var productId: Int = 0,

    @ColumnInfo(name = "productName")
    var productName: String?,

    @ColumnInfo(name = "productCount")
    var productCount: Int = 1,

    @ColumnInfo(name = "productPrice")
    var productPrice: Double?,

    @ColumnInfo(name = "productImage")
    var productImage: String?

)