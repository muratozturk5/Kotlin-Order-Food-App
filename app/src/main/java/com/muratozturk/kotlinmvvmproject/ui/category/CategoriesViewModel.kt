package com.muratozturk.kotlinmvvmproject.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.repo.Repository
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val repository = Repository()
    var categoryList: LiveData<List<Categories>> = repository.categoriesList
    var isLoading: LiveData<Repository.LOADING> = repository.isLoading


//    init {
//        getCategories()
//    }

    fun getCategories() {

        viewModelScope.launch {
            repository.getCategories()
        }
    }

}