package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.shared.RequestState
import com.example.giftcardshop.shared.toCartItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestBuyNowUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
) {
    fun doAction(giftcard: Giftcard, denomination: Denomination): Flow<RequestState<Boolean>> {
        val cartItem = giftcard.toCartItem(denomination)
        if (cartItem.payable <= 0.0) {
            throw Exception("Invalid payable amount")
        }
        return flow {
            try {
                emit(RequestState.loading(null))
                coroutineScope {
                    val result = checkoutRepository.checkout(listOf(cartItem))
                    if (result) {
                        emit(RequestState.success(result))
                    } else {
                        emit(RequestState.success(result))
                    }
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}