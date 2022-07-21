package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.incrementQuantity
import com.example.giftcardshop.shared.toCartItem
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    fun doAction(): Flow<RequestState<List<CartItem>>> {
        return flow {
            try {
                emit(RequestState.loading(null))
                cartRepository.getCartItems().collect {
                    emit(RequestState.success(it))
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}

class AddCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun doAction(giftcard: Giftcard, selectedDenomination: Denomination) {
        if (selectedDenomination.payable <= 0) {
            throw Exception("Value is equal to or less than 0")
        }
        val cartItemToAdd = giftcard.toCartItem(selectedDenomination)

        val existingCart = cartRepository.getCartItemByBrandAndValue(cartItemToAdd).first()

        if (existingCart == null) { cartRepository.addCartItem(cartItemToAdd) }
        else { cartRepository.updateCartItem(existingCart.incrementQuantity()) }
    }
}

class DeleteCartItemUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun doAction(cartItem: CartItem) {
        cartRepository.deleteCartItem(cartItem)
    }
}

class ClearCartItemsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun doAction() {
        cartRepository.clearCartItems()
    }
}