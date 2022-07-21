package com.muratozturk.kotlinmvvmproject.views.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.models.Product
import com.muratozturk.kotlinmvvmproject.repo.Repository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = Repository()
    var productList: LiveData<List<Product>> = repository.searchList
    var isLoading: LiveData<Repository.LOADING> = repository.isLoading


    fun getSearch() {
        viewModelScope.launch {
            repository.getSearch()
        }
    }
}