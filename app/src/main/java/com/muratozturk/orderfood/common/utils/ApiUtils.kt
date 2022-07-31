package com.muratozturk.orderfood.common.utils

import com.muratozturk.orderfood.data.retrofit.DAOInterface
import com.muratozturk.orderfood.data.retrofit.RetrofitClient

class ApiUtils {

    companion object {
        private const val BASE_URL = "https://codingwithlove.com/"


        fun getInterfaceDAO(): DAOInterface {
            return RetrofitClient.getClient(BASE_URL).create(DAOInterface::class.java)
        }
    }
}