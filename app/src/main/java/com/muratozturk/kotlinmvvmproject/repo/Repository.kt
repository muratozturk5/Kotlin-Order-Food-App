package com.muratozturk.kotlinmvvmproject.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.network.ApiUtils
import retrofit2.Call
import retrofit2.Response

class Repository {
    val categoriesList = MutableLiveData<List<Categories>>()
    val productList = MutableLiveData<List<Product>>()

    fun getCategories(): MutableLiveData<List<Categories>> {
        ApiUtils.getInterfaceDAO().getCategories().enqueue(
            object : retrofit2.Callback<List<Categories>> {
                override fun onResponse(
                    call: Call<List<Categories>>,
                    response: Response<List<Categories>>
                ) {
                    val tempList = response.body()
                    categoriesList.value = tempList!!
                }

                override fun onFailure(call: Call<List<Categories>>, t: Throwable) {}

            }
        )
        return categoriesList
    }

    suspend fun getProducts(categoryId: Int) {
        val response = ApiUtils.getInterfaceDAO().getProducts(categoryId)
        if (response.isSuccessful) {
            val tempList = response.body()
            productList.value = tempList!!

        }

    }
}