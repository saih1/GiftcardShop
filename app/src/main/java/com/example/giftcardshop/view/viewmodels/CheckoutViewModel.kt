package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val requestCheckoutUseCase: RequestCheckoutUseCase
) : ViewModel() {

    val _checkoutStatus: MutableStateFlow<RequestState<Boolean>> =
        MutableStateFlow(RequestState.idle(null))
    val checkoutStatus: StateFlow<RequestState<Boolean>> = _checkoutStatus

    fun requestCheckout(amount: Double) {
        requestCheckoutUseCase.doAction(amount).onEach {
            _checkoutStatus.value = it
        }.launchIn(viewModelScope)
    }

    // TODO: ResetCheckoutStatus() {}
}