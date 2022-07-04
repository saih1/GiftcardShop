package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CartItemRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.toCartItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository
) {
    fun doAction(): Flow<RequestState<List<CartItem>>> {
        return flow {
            emit(RequestState.loading(null))
            coroutineScope {
                try {
                    cartItemRepository.getCartItems().collect {
                        emit(RequestState.success(it))
                    }
                } catch (e: Exception) {
                    emit(RequestState.error(e.message, null))
                }
            }
        }
    }
}

class AddCartItemUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository
) {
    suspend fun doAction(giftcard: Giftcard, selectedValue: Double) {
        val cartItem = giftcard.toCartItem(selectedValue)
        cartItemRepository.addCartItem(cartItem)
    }
}

class DeleteCartItemUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository
) {
    suspend fun doAction(cartItem: CartItem) {
        cartItemRepository.deleteCartItem(cartItem)
    }
}

class ClearCartItemsUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository
) {
    suspend fun doAction() {
        cartItemRepository.clearCartItems()
    }
}