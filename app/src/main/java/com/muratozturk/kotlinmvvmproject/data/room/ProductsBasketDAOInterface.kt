package com.muratozturk.kotlinmvvmproject.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel

@Dao
interface ProductsBasketDAOInterface {

    @Insert
    suspend fun addProductBasket(productsBasketRoomModel: ProductsBasketRoomModel)

    @Query("SELECT * FROM productsbasketdatabase")
    suspend fun getProductsBasket(): List<ProductsBasketRoomModel>?

    @Query("SELECT Sum(ProductPrice) FROM productsbasketdatabase")
    suspend fun getProductsBasketTotalAmount(): Double?

    @Query("SELECT * FROM productsbasketdatabase WHERE id = :productId  LIMIT 1")
    suspend fun getProductById(productId: Int): ProductsBasketRoomModel?

    @Query("DELETE FROM productsbasketdatabase WHERE id = :idInput")
    suspend fun deleteProductWithId(idInput: Int)

    @Query("DELETE FROM productsbasketdatabase")
    suspend fun clearBasket()

    @Query("UPDATE productsbasketdatabase SET productCount=:productCount, productPrice=:productPrice  WHERE id = :productId")
    suspend fun updateProductBasket(productId: Int, productCount: Int, productPrice: Double?)
}