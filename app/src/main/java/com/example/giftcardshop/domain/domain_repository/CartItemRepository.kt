package com.example.giftcardshop.domain.domain_repository

import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartItemRepository {
    fun getCartItems(): Flow<List<CartItem>>

    fun getCartItemById(id: Int): Flow<CartItem>

    suspend fun addCartItem(cartItem: CartItem)

    suspend fun deleteCartItem(cartItem: CartItem)

    suspend fun clearCartItems()
}