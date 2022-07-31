package com.muratozturk.orderfood.data.retrofit


import com.muratozturk.orderfood.data.models.Categories
import com.muratozturk.orderfood.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DAOInterface {

    @GET("getCategories")
    suspend fun getCategories(): Response<List<Categories>>

    @GET("getCategoryProducts")
    suspend fun getProducts(@Query("categoryId") categoryId: Int): Response<List<Product>>

    @GET("getProducts")
    suspend fun getSearch(): Response<List<Product>>
}