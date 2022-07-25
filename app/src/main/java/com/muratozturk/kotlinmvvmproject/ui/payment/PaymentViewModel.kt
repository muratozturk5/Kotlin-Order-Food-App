package com.muratozturk.kotlinmvvmproject.ui.payment

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muratozturk.kotlinmvvmproject.data.repo.Repository
import kotlinx.coroutines.launch

class PaymentViewModel(context: Context) : ViewModel() {

    private val repository = Repository(context)
    
    fun clearBasket() {
        viewModelScope.launch {
            repository.clearBasket()
        }
    }
}