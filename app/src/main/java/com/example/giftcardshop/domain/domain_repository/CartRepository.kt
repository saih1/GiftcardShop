package com.example.giftcardshop.domain.domain_repository

import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItem>>

    suspend fun addCartItem(cartItem: CartItem)

    suspend fun deleteCartItem(cartItem: CartItem)

    suspend fun clearCartItems()

    suspend fun getCartItemByBrandAndValue(cartItem: CartItem): Flow<CartItem?>

    suspend fun updateCartItem(cartItem: CartItem)
}