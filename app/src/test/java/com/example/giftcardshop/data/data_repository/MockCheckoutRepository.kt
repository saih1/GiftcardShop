package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.CheckoutRepository

class MockCheckoutRepository : CheckoutRepository {
    override suspend fun checkout(amount: Double): Boolean {
        TODO("Not yet implemented")
    }
}