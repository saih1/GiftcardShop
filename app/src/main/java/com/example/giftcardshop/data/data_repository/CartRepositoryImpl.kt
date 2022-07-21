package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.local.CartItemDao
import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.model.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartItemDao: CartItemDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartItemDao.getCartItems()
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

    override suspend fun getCartItemByBrandAndValue(cartItem: CartItem): Flow<CartItem?> {
        return cartItemDao.getCartItem(cartItem.brand, cartItem.value)
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        return cartItemDao.updateCartItem(cartItem)
    }
}