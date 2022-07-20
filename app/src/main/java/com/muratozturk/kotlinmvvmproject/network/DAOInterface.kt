package com.muratozturk.kotlinmvvmproject.network


import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DAOInterface {

    @GET("JsonCategories")
    fun getCategories(): Call<List<Categories>>

    @GET("JsonItems/{id}")
    suspend fun getProducts(@Path("id") id: Int): Response<List<Product>>

    @GET("JsonSearch")
    suspend fun getSearch(): Response<List<Product>>
}