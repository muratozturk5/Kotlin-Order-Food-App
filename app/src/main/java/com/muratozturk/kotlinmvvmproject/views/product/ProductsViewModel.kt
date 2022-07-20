package com.muratozturk.kotlinmvvmproject.views.product


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val repository = Repository()
    var productList: MutableLiveData<List<Product>> = repository.productList
    var isLoading: MutableLiveData<Repository.LOADING> = repository.isLoading


    fun getProducts(categoryId: Int) {
        viewModelScope.launch {
            repository.getProducts(categoryId)
        }


    }

}