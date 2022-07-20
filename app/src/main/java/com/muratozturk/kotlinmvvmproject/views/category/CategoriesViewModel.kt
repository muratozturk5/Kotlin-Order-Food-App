package com.muratozturk.kotlinmvvmproject.views.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.kotlinmvvmproject.models.Categories
import com.muratozturk.kotlinmvvmproject.repo.Repository

class CategoriesViewModel : ViewModel() {

    private val repository = Repository()
    private var _categoryList = MutableLiveData<List<Categories>>()
    var isLoading: MutableLiveData<Repository.LOADING> = repository.isLoading

    val categoryList: LiveData<List<Categories>>
        get() = _categoryList

    init {
        getCategories()
    }

    private fun getCategories() {
        _categoryList = repository.getCategories()
    }

}