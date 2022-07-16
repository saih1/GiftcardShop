package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.example.giftcardshop.domain.use_case.RequestBuyNowUseCase
import com.example.giftcardshop.domain.use_case.RequestCheckoutUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val requestCheckoutUseCase: RequestCheckoutUseCase,
    private val requestBuyNowUseCase: RequestBuyNowUseCase
) : ViewModel() {

    private val _checkoutStatus: MutableStateFlow<RequestState<Boolean>> =
        MutableStateFlow(RequestState.idle(null))
    val checkoutStatus: StateFlow<RequestState<Boolean>> = _checkoutStatus

    fun requestCheckout(cartItems: List<CartItem>) {
        requestCheckoutUseCase.doAction(cartItems).onEach {
            _checkoutStatus.value = it
        }.launchIn(viewModelScope)
    }

    fun requestBuyNow(giftcard: Giftcard, denomination: Denomination) {
        requestBuyNowUseCase.doAction(giftcard, denomination).onEach {
            _checkoutStatus.value = it
        }.launchIn(viewModelScope)
    }
}