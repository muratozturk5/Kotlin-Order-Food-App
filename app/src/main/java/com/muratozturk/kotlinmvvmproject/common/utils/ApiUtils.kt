package com.muratozturk.kotlinmvvmproject.common.utils

import com.muratozturk.kotlinmvvmproject.data.retrofit.DAOInterface
import com.muratozturk.kotlinmvvmproject.data.retrofit.RetrofitClient

class ApiUtils {

    companion object {
        private const val BASE_URL = "https://liwapos.com/samba.mobil/Json/"


        fun getInterfaceDAO(): DAOInterface {
            return RetrofitClient.getClient(BASE_URL).create(DAOInterface::class.java)
        }
    }
}