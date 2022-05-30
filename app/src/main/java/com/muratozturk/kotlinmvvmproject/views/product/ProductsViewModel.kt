package com.muratozturk.kotlinmvvmproject.views.product


import androidx.lifecycle.MutableLiveData
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository

class ProductsViewModel(categoryId: Int) {

    private val repository = Repository()
    var productList = MutableLiveData<List<Product>>()

    init {
        getProducts(categoryId)
    }

    private fun getProducts(categoryId: Int) {
        productList = repository.getProducts(categoryId)

    }

}