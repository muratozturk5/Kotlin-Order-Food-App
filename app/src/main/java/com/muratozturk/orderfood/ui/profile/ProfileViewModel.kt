package com.muratozturk.orderfood.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.muratozturk.orderfood.data.models.UserModel
import com.muratozturk.orderfood.data.repo.UserRepository

class ProfileViewModel : ViewModel() {

    private val usersRepo = UserRepository()

    val userInfo: LiveData<UserModel>
        get() = usersRepo.userInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        usersRepo.getUserInfo()
    }

    fun signOut() {
        usersRepo.signOut()
    }
}