package com.muratozturk.orderfood.data.repo

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muratozturk.orderfood.data.models.UserModel

class UserRepository {

    enum class LOADING {
        LOADING, DONE, ERROR
    }


    var isSignIn = MutableLiveData<Boolean>()

    var isSignUp = MutableLiveData<Boolean>()

    var userInfo = MutableLiveData<UserModel>()
    var isLoading = MutableLiveData<LOADING>()

    private var auth = Firebase.auth
    private val db = Firebase.firestore

    fun signIn(eMail: String, password: String) {
        isLoading.value = LOADING.LOADING
        auth.signInWithEmailAndPassword(eMail, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                isLoading.value = LOADING.DONE
                isSignIn.value = true
                Log.d("SIGN_IN", "SUCCESS")
            } else {
                isLoading.value = LOADING.ERROR
                isSignIn.value = false
                Log.w("SIGN_IN", "FAILURE", task.exception)
            }
        }
    }

    fun signUp(eMail: String, password: String, phoneNumber: String) {
        isLoading.value = LOADING.LOADING
        auth.createUserWithEmailAndPassword(eMail, password).addOnCompleteListener { task ->

            if (task.isSuccessful) {

                val currentUser = auth.currentUser
                currentUser?.let { fbUser ->
                    val user = hashMapOf(
                        "id" to fbUser.uid,
                        "email" to eMail,
                        "phonenumber" to phoneNumber
                    )

                    db.collection("users").document(fbUser.uid)
                        .set(user)
                        .addOnSuccessListener {
                            isSignUp.value = true
                            isLoading.value = LOADING.DONE
                            Log.d("SIGN_UP", "SUCCESS")
                        }
                        .addOnFailureListener { e ->
                            isSignUp.value = false
                            isLoading.value = LOADING.ERROR
                            Log.w("SIGN_UP", "FAILURE", e)
                        }
                }

            } else {
                isSignUp.value = false
                isLoading.value = LOADING.ERROR
                Log.w("SIGN_UP", "FAILURE", task.exception)
            }
        }
    }

    fun getUserInfo() {
        isLoading.value = LOADING.LOADING
        auth.currentUser?.let { user ->

            val docRef = db.collection("users").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    document?.let {
                        userInfo.value = UserModel(
                            user.email,
                            document.get("phonenumber") as String
                        )
                        isLoading.value = LOADING.DONE
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                    isLoading.value = LOADING.ERROR
                }
        }
    }

    fun signOut() {
        isLoading.value = LOADING.LOADING
        auth.signOut()
        isLoading.value = LOADING.DONE
    }
}