package com.example.giftcardshop.shared

import com.example.giftcardshop.data.network.dto.DenominationDto
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

fun GiftcardDto.toGiftcard(): Giftcard {
    return Giftcard(
        image = image,
        brand = brand,
        discount = discount,
        terms = terms,
        denomination = denominationDtoList
            .toDenominationList(discount),
        vendor = vendor
    )
}

fun List<DenominationDto>.toDenominationList(discount: Double): List<Denomination> =
    map {
        Denomination(
            price = it.price,
            currency = it.currency,
            stock = it.stock,
            payable = it.price.discountedPrice(discount)
    )
}

fun Giftcard.toCartItem(selectedDenomination: Denomination): CartItem {
    return CartItem(
        brand = brand,
        value = selectedDenomination.price,
        image = image,
        vendor = vendor,
        payable = selectedDenomination.payable,
        quantity = 1,
        totalPayable = selectedDenomination.payable
    )
}

fun CartItem.incrementQuantity(): CartItem {
    return CartItem(
        id = this.id,
        brand = this.brand,
        value = this.value,
        image = this.image,
        vendor = this.vendor,
        payable = payable,
        quantity = (this.quantity + 1),
        totalPayable = (this.totalPayable + this.payable)
    )
}

fun Double.discountedPrice(discount: Double): Double = (this * discount) / 100

fun List<CartItem>.calculateTotal(): Double = this.sumOf(CartItem::totalPayable)

fun Double.toPercentage(): Int = (100 - this).roundToInt()

fun Double.roundToTwoDecimal(): Double =
    BigDecimal(this).setScale(2, RoundingMode.FLOOR).toDouble()