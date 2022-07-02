package com.example.giftcardshop.view.cart_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giftcardshop.domain.domain_repository.CartItemRepository
import com.example.giftcardshop.domain.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository
) : ViewModel() {

    fun addCart() {
        val fakeCartItem = CartItem(
            brand = "brand",
            value = 10.0,
            image = "fakeImage",
            vendor = "vendor"
        )
        viewModelScope.launch(Dispatchers.IO) {
            cartItemRepository.addCartItem(fakeCartItem)
        }
    }
}