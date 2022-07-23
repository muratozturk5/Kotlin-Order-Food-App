package com.muratozturk.kotlinmvvmproject.ui.category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.data.models.Categories
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import kotlinx.coroutines.launch

class CategoriesViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    var categoryList: LiveData<List<Categories>> = repository.categoriesList
    var isLoading: LiveData<Repository.LOADING> = repository.isLoading


    fun getCategories() {

        viewModelScope.launch {
            repository.getCategories()
        }
    }

}