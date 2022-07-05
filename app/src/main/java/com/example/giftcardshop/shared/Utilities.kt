package com.example.giftcardshop.shared

import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Giftcard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun GiftcardDto.toGiftcard(): Giftcard {
    return Giftcard(
        image = this.image,
        brand = this.brand,
        discount = this.discount,
        terms = this.terms,
        denominations = this.denominations,
        vendor = this.vendor
    )
}

fun Giftcard.toCartItem(selectedValue: Double): CartItem {
    return CartItem(
        brand = this.brand,
        value = selectedValue,
        image = this.image,
        vendor = this.vendor
    )
}