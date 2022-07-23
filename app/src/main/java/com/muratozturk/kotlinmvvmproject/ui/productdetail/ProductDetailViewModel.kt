package com.muratozturk.kotlinmvvmproject.ui.productdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import kotlinx.coroutines.launch

class ProductDetailViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    fun addBookToBasket(productModel: ProductsBasketRoomModel) {


        viewModelScope.launch {
            repository.addBookToBasket(productModel)
        }

    }

}