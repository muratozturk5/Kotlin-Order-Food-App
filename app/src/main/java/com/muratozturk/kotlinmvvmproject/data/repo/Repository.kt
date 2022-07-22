package com.muratozturk.kotlinmvvmproject.data.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.muratozturk.kotlinmvvmproject.data.models.Categories
import com.muratozturk.kotlinmvvmproject.data.models.Product
import com.muratozturk.kotlinmvvmproject.common.utils.ApiUtils
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.data.retrofit.DAOInterface
import com.muratozturk.kotlinmvvmproject.data.room.ProductsBasketDAOInterface
import com.muratozturk.kotlinmvvmproject.data.room.ProductsBasketRoomDatabase

class Repository(context: Context) {
    enum class LOADING {
        LOADING, DONE, ERROR
    }

    val categoriesList = MutableLiveData<List<Categories>>()
    val productList = MutableLiveData<List<Product>>()
    val searchList = MutableLiveData<List<Product>>()
    val basketList = MutableLiveData<List<ProductsBasketRoomModel>>()
    val isLoading = MutableLiveData<LOADING>()

    private val dif: DAOInterface = ApiUtils.getInterfaceDAO()
    private val basketDif: ProductsBasketDAOInterface? =
        ProductsBasketRoomDatabase.productsBasketRoomDatabase(context)?.productsBasketDAOInterface()

    suspend fun getCategories() {
        isLoading.value = LOADING.LOADING
        val response = dif.getCategories()
        if (response.isSuccessful) {
            val tempList = response.body()
            categoriesList.value = tempList!!
            isLoading.value = LOADING.DONE
        } else {
            isLoading.value = LOADING.ERROR
        }
    }

    suspend fun getProducts(categoryId: Int) {
        isLoading.value = LOADING.LOADING
        val response = dif.getProducts(categoryId)
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
        val response = dif.getSearch()
        if (response.isSuccessful) {
            val tempList = response.body()
            searchList.value = tempList!!
            isLoading.value = LOADING.DONE
        } else {
            isLoading.value = LOADING.ERROR

        }
    }

    fun getBasket() {
        isLoading.value = LOADING.LOADING

        basketDif?.getProductsBasket()?.let {
            basketList.value = it
            isLoading.value = LOADING.DONE
        } ?: run {
            isLoading.value = LOADING.DONE
        }
    }

    fun addBookToBasket(productModel: ProductsBasketRoomModel) {
        basketDif?.getProductsBasket()?.let {

            if (it.equals(productModel.productId).not()) {
                basketDif.addProductBasket(productModel)
            } else {
                val productCount = productModel.productCount + 1
                val productPrice =
                    (productModel.productPrice?.div(productModel.productCount))?.times(productCount)
                basketDif.updateProductBasket(productModel.productId, productCount, productPrice)
            }

        }
    }

    fun deleteBookFromBasket(productId: Int) {
        basketDif?.deleteProductWithId(productId)
    }

    fun clearBasket() {
        basketDif?.clearBasket()
    }
}