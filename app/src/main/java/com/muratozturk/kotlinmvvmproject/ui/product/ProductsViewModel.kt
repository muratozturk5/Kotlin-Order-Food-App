package com.muratozturk.kotlinmvvmproject.ui.product


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.data.models.Product
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import kotlinx.coroutines.launch

class ProductsViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)
    var productList: LiveData<List<Product>> = repository.productList
    var isLoading: LiveData<Repository.LOADING> = repository.isLoading


    fun getProducts(categoryId: Int) {
        viewModelScope.launch {
            repository.getProducts(categoryId)
        }


    }

}