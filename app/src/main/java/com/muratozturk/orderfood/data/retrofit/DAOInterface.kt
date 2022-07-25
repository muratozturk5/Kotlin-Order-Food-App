package com.muratozturk.orderfood.data.retrofit


import com.muratozturk.orderfood.data.models.Categories
import com.muratozturk.orderfood.data.models.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DAOInterface {

    @GET("JsonCategories")
    suspend fun getCategories(): Response<List<Categories>>

    @GET("JsonItems/{id}")
    suspend fun getProducts(@Path("id") id: Int): Response<List<Product>>

    @GET("JsonSearch")
    suspend fun getSearch(): Response<List<Product>>
}