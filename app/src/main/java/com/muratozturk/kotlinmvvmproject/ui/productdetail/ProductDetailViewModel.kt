package com.muratozturk.kotlinmvvmproject.ui.productdetail

import android.content.Context
import androidx.lifecycle.ViewModel
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel
import com.muratozturk.kotlinmvvmproject.data.repo.Repository

class ProductDetailViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)

    fun addBookToBasket(productModel: ProductsBasketRoomModel) {
        repository.addBookToBasket(productModel)
    }

}