package com.muratozturk.kotlinmvvmproject.views.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.repo.Repository
import kotlinx.coroutines.launch

class BasketViewModel : ViewModel() {

    private val repository = Repository()
    private var _categoryList: MutableLiveData<List<Categories>> = repository.categoriesList
    var isLoading: MutableLiveData<Repository.LOADING> = repository.isLoading

    val categoryList: LiveData<List<Categories>>
        get() = _categoryList


    fun getCategories() {

        viewModelScope.launch {
            repository.getCategories()
        }
    }
}