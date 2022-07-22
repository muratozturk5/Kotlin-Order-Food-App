package com.muratozturk.kotlinmvvmproject.ui.basket

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.data.models.Categories
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import kotlinx.coroutines.launch

class BasketViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)
    var basketList: LiveData<List<ProductsBasketRoomModel>> = repository.basketList
    var isLoading: LiveData<Repository.LOADING> = repository.isLoading

    fun getBasket() {
        repository.getBasket()
    }
}