package com.example.giftcardshop.data.fake_apis

import kotlinx.coroutines.delay

class FakeCheckoutApi {
    suspend fun requestCheckout(amount: Double): Boolean {
        delay(1500)
        if (amount <= 0)  return false
        return true
    }
}