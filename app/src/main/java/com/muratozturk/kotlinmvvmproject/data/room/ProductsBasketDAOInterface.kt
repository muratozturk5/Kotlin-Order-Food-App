package com.muratozturk.kotlinmvvmproject.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel

@Dao
interface ProductsBasketDAOInterface {

    @Insert
    fun addProductBasket(productsBasketRoomModel: ProductsBasketRoomModel)

    @Query("SELECT * FROM productsbasketdatabase")
    fun getProductsBasket(): List<ProductsBasketRoomModel>?

    @Query("DELETE FROM productsbasketdatabase WHERE id = :idInput")
    fun deleteProductWithId(idInput: Int)

    @Query("DELETE FROM productsbasketdatabase")
    fun clearBasket()

    @Query("UPDATE productsbasketdatabase SET productCount=:productCount, productPrice=:productPrice  WHERE id = :productId")
    fun updateProductBasket(productId: Int, productCount: Int, productPrice: Double?)
}