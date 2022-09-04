package com.muratozturk.orderfood.ui.product


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.orderfood.data.models.Product
import com.muratozturk.orderfood.data.repo.Repository
import kotlinx.coroutines.delay
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