package com.example.giftcardshop.data.data_repository

import com.example.giftcardshop.data.fake_apis.FakeCheckoutApi
import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import com.example.giftcardshop.domain.model.CartItem
import javax.inject.Inject

class CheckoutRepositoryImpl @Inject constructor(
    private val checkoutApi: FakeCheckoutApi
) : CheckoutRepository {
    override suspend fun checkout(cartItems: List<CartItem>): Boolean {
        return checkoutApi.requestCheckout(cartItems)
    }
}