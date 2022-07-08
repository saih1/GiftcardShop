package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

class MockCartRepository : CartRepository{
    override fun getCartItems(): Flow<List<CartItem>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCartItems() {
        TODO("Not yet implemented")
    }
}