package com.muratozturk.orderfood.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.orderfood.data.repo.UserRepository

class SignInViewModel : ViewModel() {

    private var usersRepo = UserRepository()
    val isSignIn: LiveData<Boolean> = usersRepo.isSignIn
    val isLoading: LiveData<UserRepository.LOADING> = usersRepo.isLoading


    fun signIn(eMail: String, password: String) {
        usersRepo.signIn(eMail, password)
    }
}