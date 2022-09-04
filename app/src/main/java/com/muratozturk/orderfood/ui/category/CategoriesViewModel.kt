package com.muratozturk.orderfood.ui.category

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.orderfood.data.models.Categories
import com.muratozturk.orderfood.data.repo.Repository
import kotlinx.coroutines.delay
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