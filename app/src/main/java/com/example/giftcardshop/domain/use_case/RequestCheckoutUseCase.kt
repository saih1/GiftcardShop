package com.example.giftcardshop.domain.use_case

import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import com.example.giftcardshop.shared.RequestState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestCheckoutUseCase @Inject constructor(
    private val checkoutRepository: CheckoutRepository
) {
    fun doAction(amount: Double): Flow<RequestState<Boolean>> {
        return flow {
            emit(RequestState.loading(null))
            try {
                coroutineScope {
                    val result = checkoutRepository.checkout(amount)
                    emit(RequestState.success(result))
                }
            } catch (e: Exception) {
                emit(RequestState.error(e.message, null))
            }
        }
    }
}