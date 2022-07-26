package com.muratozturk.orderfood.ui.login.signin

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.orderfood.data.repo.UserRepository

class SignInViewModel : ViewModel() {

    private var usersRepo = UserRepository()
    val isSignIn: LiveData<Boolean> = usersRepo.isSignIn
    val isLoading: LiveData<UserRepository.LOADING> = usersRepo.isLoading


    private var _isValidMail = MutableLiveData<Boolean>()
    val isValidMail: LiveData<Boolean> = _isValidMail

    private var _isInfosValid = MutableLiveData<Boolean>()
    val isInfosValid: LiveData<Boolean> = _isInfosValid

    fun signIn(eMail: String, password: String) {


        if (eMail.isEmpty() || password.isEmpty()) {
            _isInfosValid.value = false
        } else {
            _isInfosValid.value = true
            if (Patterns.EMAIL_ADDRESS.matcher(eMail).matches().not()) {
                _isValidMail.value = false
            } else {
                _isValidMail.value = true

                usersRepo.signIn(eMail, password)
            }

        }
    }
}