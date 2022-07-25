package com.muratozturk.orderfood.ui.productdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.orderfood.data.models.ProductsBasketRoomModel
import com.muratozturk.orderfood.data.repo.Repository
import kotlinx.coroutines.launch

class ProductDetailViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    fun addBookToBasket(productModel: ProductsBasketRoomModel) {


        viewModelScope.launch {
            repository.addBookToBasket(productModel)
        }

    }

}