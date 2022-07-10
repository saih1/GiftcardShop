package com.example.giftcardshop.shared

import android.text.Html
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Giftcard
import kotlin.math.roundToInt

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

fun List<CartItem>.calculateTotal(): Double = this.sumOf(CartItem::value)

fun Double.discountPercentage(): Int = (100 - this).roundToInt()

fun String.toStringFromHTML(): String = Html
    .fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    .toString()