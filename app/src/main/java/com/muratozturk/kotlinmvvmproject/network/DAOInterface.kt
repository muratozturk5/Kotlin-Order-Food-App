package com.muratozturk.kotlinmvvmproject.network


import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DAOInterface {

    @GET("JsonCategories")
    fun getCategories(): Call<List<Categories>>

    @GET("JsonItems/{id}")
    fun getProducts(@Path("id") id: Int): Call<List<Product>>
}