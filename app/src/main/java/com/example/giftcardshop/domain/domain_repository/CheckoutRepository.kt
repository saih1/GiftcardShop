package com.example.giftcardshop.domain.domain_repository

import com.example.giftcardshop.domain.model.CartItem

interface CheckoutRepository {
    suspend fun checkout(cartItems: List<CartItem>): Boolean
}
