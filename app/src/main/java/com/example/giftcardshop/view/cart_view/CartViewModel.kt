package com.example.giftcardshop.view.cart_view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.use_case.AddCartItemUseCase
import com.example.giftcardshop.domain.use_case.ClearCartItemsUseCase
import com.example.giftcardshop.domain.use_case.DeleteCartItemUseCase
import com.example.giftcardshop.domain.use_case.GetCartItemsUseCase
import com.example.giftcardshop.shared.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val clearCartItemsUseCase: ClearCartItemsUseCase
) : ViewModel() {

    // Just for testing
    private val fakeCartItem = CartItem(
        brand = "brand",
        value = 10.0,
        image = "fakeImage",
        vendor = "vendor"
    )

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

    fun addToCart() {
        viewModelScope.launch(Dispatchers.IO) {
            addCartItemUseCase.doAction(fakeCartItem)
        }
    }

    fun deleteFromCart() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCartItemUseCase.doAction(fakeCartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            clearCartItemsUseCase.doAction()
        }
    }
}