package com.example.giftcardshop.shared

import kotlinx.coroutines.delay

class FakeAuthenticator() {
    suspend fun signIn(username: String, password: String) : Boolean {
        delay(1500)
        return username == "admin@admin.com" && password == "password"
    }

    suspend fun signOut() :Boolean {
        delay(1500)
        return true
    }
}