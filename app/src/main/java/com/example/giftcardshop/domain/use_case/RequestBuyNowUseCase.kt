package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import com.example.giftcardshop.shared.RequestState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestBuyNowUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository,
) {
    fun doAction(amount: Double): Flow<RequestState<Boolean>> {
        if (amount <= 0.0) {
            throw Exception("Invalid amount")
        }
        return flow {
            try {
                emit(RequestState.loading(null))
                coroutineScope {
                    val result = checkoutRepository.checkout(amount)
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