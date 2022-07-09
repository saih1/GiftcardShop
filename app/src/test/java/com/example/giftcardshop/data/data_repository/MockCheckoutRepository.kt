package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import kotlinx.coroutines.delay

class MockCheckoutRepository : CheckoutRepository {
    private var checkoutReturnBoolean = true
    private var throwException = false

    override suspend fun checkout(amount: Double): Boolean {
        delay(150)
        if (throwException)
            throw Exception("Checkout error")
        return checkoutReturnBoolean
    }

    // Accessor
    fun checkoutReturnBoolean(boolean: Boolean) {
        checkoutReturnBoolean = boolean
    }

    fun throwException(boolean: Boolean) {
        throwException = boolean
    }
}