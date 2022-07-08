package com.example.giftcardshop.data.fake_apis

import kotlinx.coroutines.delay

class FakeAuthenticationApi() {
    suspend fun requestLogin(username: String, password: String): Boolean {
        delay(1500)
        return username == "admin@admin.com" && password == "password"
    }

    suspend fun requestLogout(): Boolean {
        delay(1500)
        return true
    }
}