package com.example.giftcardshop.domain.domain_repository

interface CheckoutRepository {
    suspend fun checkout(amount: Double): Boolean
}
