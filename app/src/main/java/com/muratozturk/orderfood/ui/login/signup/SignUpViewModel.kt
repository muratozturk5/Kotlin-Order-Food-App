package com.muratozturk.orderfood.ui.login.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.orderfood.data.repo.UserRepository

class SignUpViewModel : ViewModel() {

    private var usersRepo = UserRepository()


    private var _isInfosValid = MutableLiveData<Boolean>()
    val isInfosValid: LiveData<Boolean> = _isInfosValid

    private var _isValidMail = MutableLiveData<Boolean>()
    val isValidMail: LiveData<Boolean> = _isValidMail

    private var _isPasswordMatch = MutableLiveData<Boolean>()
    val isPasswordMatch: LiveData<Boolean> = _isPasswordMatch

    val isSignUp: LiveData<Boolean> = usersRepo.isSignUp


    fun signUp(
        eMail: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String
    ) {

        if (eMail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty()) {
            _isInfosValid.value = false
        } else {

            if (Patterns.EMAIL_ADDRESS.matcher(eMail).matches().not()) {
                _isValidMail.value = false
            } else {
                if (password != confirmPassword) {
                    _isPasswordMatch.value = false
                } else {
                    usersRepo.signUp(eMail, password, phoneNumber)
                }
            }
        }
    }
}