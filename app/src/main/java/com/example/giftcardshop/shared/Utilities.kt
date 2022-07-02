package com.example.giftcardshop.shared

import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.Giftcard

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