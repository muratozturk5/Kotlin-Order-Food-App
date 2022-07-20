package com.muratozturk.kotlinmvvmproject.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.network.ApiUtils
import com.muratozturk.kotlinmvvmproject.network.DAOInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    enum class LOADING {
        LOADING, DONE, ERROR
    }

    val categoriesList = MutableLiveData<List<Categories>>()
    val productList = MutableLiveData<List<Product>>()
    val searchList = MutableLiveData<List<Product>>()
    val isLoading = MutableLiveData<LOADING>()

    private val dif: DAOInterface = ApiUtils.getInterfaceDAO()
    fun getCategories(): MutableLiveData<List<Categories>> {
        isLoading.value = LOADING.LOADING
        dif.getCategories().enqueue(object : Callback<List<Categories>> {
            override fun onResponse(
                call: Call<List<Categories>>,
                response: Response<List<Categories>>
            ) {
                val tempList = response.body()
                categoriesList.value = tempList!!
                isLoading.value = LOADING.DONE
            }

            override fun onFailure(call: Call<List<Categories>>, t: Throwable) {
                isLoading.value = LOADING.ERROR
            }

        }
        )
        return categoriesList
    }

    suspend fun getProducts(categoryId: Int) {
        isLoading.value = LOADING.LOADING
        val response = ApiUtils.getInterfaceDAO().getProducts(categoryId)
        if (response.isSuccessful) {
            val tempList = response.body()
            productList.value = tempList!!
            isLoading.value = LOADING.DONE
        } else {
            isLoading.value = LOADING.ERROR
        }
    }

    suspend fun getSearch() {
        isLoading.value = LOADING.LOADING
        val response = ApiUtils.getInterfaceDAO().getSearch()
        if (response.isSuccessful) {
            val tempList = response.body()
            searchList.value = tempList!!
            isLoading.value = LOADING.DONE
        } else {
            isLoading.value = LOADING.ERROR

        }
    }
}