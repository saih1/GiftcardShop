package com.example.giftcardshop.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.di.Dispatcher
import com.example.giftcardshop.di.GiftcardDispatchers
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.use_case.ClearCartItemsUseCase
import com.example.giftcardshop.domain.use_case.DeleteCartItemUseCase
import com.example.giftcardshop.domain.use_case.GetCartItemsUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val clearCartItemsUseCase: ClearCartItemsUseCase,
    @Dispatcher(GiftcardDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _cartItems: MutableStateFlow<RequestState<List<CartItem>>> =
        MutableStateFlow(RequestState.idle(null))
    val cartItems: StateFlow<RequestState<List<CartItem>>> = _cartItems

    init {
        getAllCartItems()
    }

    private fun getAllCartItems() {
        getCartItemsUseCase.doAction().onEach {
            _cartItems.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteFromCart(cartItem: CartItem) {
        viewModelScope.launch(ioDispatcher) {
            deleteCartItemUseCase.doAction(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch(ioDispatcher) {
            clearCartItemsUseCase.doAction()
        }
    }
}