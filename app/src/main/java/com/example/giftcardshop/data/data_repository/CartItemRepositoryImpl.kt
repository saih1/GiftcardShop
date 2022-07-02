package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.local.CartItemDao
import com.example.giftcardshop.domain.domain_repository.CartItemRepository
import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val cartItemDao: CartItemDao
) : CartItemRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartItemDao.getCartItems()
    }

    override fun getCartItemById(id: Int): Flow<CartItem> {
        return cartItemDao.getCartItemById(id)
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        return cartItemDao.addCartItem(cartItem)
    }

    override suspend fun deleteCartItem(cartItem: CartItem) {
        return cartItemDao.deleteCartItem(cartItem)
    }

    override suspend fun clearCartItems() {
        return cartItemDao.clearCartItems()
    }
}