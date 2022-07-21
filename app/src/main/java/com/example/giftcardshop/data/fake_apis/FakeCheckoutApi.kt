package com.example.giftcardshop.data.fake_apis

import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.calculateTotal
import kotlinx.coroutines.delay

class FakeCheckoutApi {
    suspend fun requestCheckout(cartItems: List<CartItem>): Boolean {
        delay(1500)
        if (cartItems.calculateTotal() <= 0)  return false
        return true
    }
}