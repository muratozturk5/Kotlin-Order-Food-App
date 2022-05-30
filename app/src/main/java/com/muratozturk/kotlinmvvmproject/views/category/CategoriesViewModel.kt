package com.muratozturk.kotlinmvvmproject.views.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.repo.Repository

class CategoriesViewModel : ViewModel() {

    private val repository = Repository()
    var categoryList = MutableLiveData<List<Categories>>()

    init {
        getCategories()
    }

    private fun getCategories() {
        categoryList = repository.getCategories()
    }

}