package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.calculateTotal
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestCheckoutUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
    private val cartRepository: CartRepository
) {
    fun doAction(cartItems: List<CartItem>): Flow<RequestState<Boolean>> {
        if (cartItems.calculateTotal() <= 0.0) {
            throw Exception("Invalid total amount")
        }
        return flow {
            try {
                emit(RequestState.loading(null))
                val result = checkoutRepository.checkout(cartItems)
                if (result) {
                    emit(RequestState.success(result))
                    cartRepository.clearCartItems()
                } else {
                    emit(RequestState.success(result))
                }

            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}