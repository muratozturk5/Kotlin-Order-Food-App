package com.muratozturk.orderfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.orderfood.data.repo.UserRepository

class SignInViewModel : ViewModel() {

    private var usersRepo = UserRepository()

    private var _isSignIn = MutableLiveData<Boolean>()
    val isSignIn: LiveData<Boolean>
        get() = _isSignIn

    init {
        _isSignIn = usersRepo.isSignIn
    }

    fun signIn(eMail: String, password: String) {
        usersRepo.signIn(eMail, password)
    }
}