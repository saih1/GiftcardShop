package com.example.giftcardshop.shared

import com.example.giftcardshop.data.network.dto.DenominationDto
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Denomination
import com.example.giftcardshop.domain.model.Giftcard
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilitiesKtTest {

    @Test
    fun toDenominationList() {
        val discount = 90.0
        val denominationDtoList = listOf(
            DenominationDto(price = 20.0, currency = "", stock = ""),
            DenominationDto(price = 50.0, currency = "", stock = ""))
        val expectedDenominationList = listOf(
            Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
            Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
        )
        assertThat(denominationDtoList.toDenominationList(discount))
            .isEqualTo(expectedDenominationList)
    }

    @Test
    fun toGiftcard() {
        val giftcardDto = GiftcardDto(brand = "", cardTypeStatus = "",
            disclaimer = "", id = "", image = "", importantContent = "",
            position = 0, terms = "", vendor = "", discount = 90.0,
            denominationDtoList = listOf(
                DenominationDto(price = 20.0, currency = "", stock = ""),
                DenominationDto(price = 50.0, currency = "", stock = ""))
        )
        val expectedGiftcard = Giftcard(
            image = "", brand = "", discount = 90.0, terms = "", vendor = "",
            denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0)
            )
        )
        assertThat(giftcardDto.toGiftcard())
            .isEqualTo(expectedGiftcard)
    }

    @Test
    fun toCartItem() {
        val giftcard = Giftcard(
            image = "", brand = "", discount = 90.0, terms = "", vendor = "",
            denomination = listOf(
                Denomination(price = 20.0, currency = "", stock = "", payable = 18.0),
                Denomination(price = 50.0, currency = "", stock = "", payable = 45.0))
        )
        val selectedDenomination = giftcard.denomination[0]

        val expectedCartItem = CartItem(
            id = 0, brand = "", value = 20.0,
            image = "", vendor = "", payable = 18.0,
            totalPayable = 18.0, quantity = 1)

        assertThat(giftcard.toCartItem(selectedDenomination))
            .isEqualTo(expectedCartItem)
    }

    @Test
    fun discountedPrice() {
        val discount = 90.0
        val originalPrice = 100.0
        val priceAfterDiscount = 90.0

        assertThat(originalPrice.discountedPrice(discount))
            .isEqualTo(priceAfterDiscount)
    }

    @Test
    fun calculateTotal() {
        val cartItems = listOf(
            CartItem(brand = "", value = 0.0, image = "",
                vendor = "", payable = 10.0, totalPayable = 10.0, quantity = 1),
            CartItem(brand = "", value = 0.0, image = "",
                vendor = "", payable = 20.0, totalPayable = 20.0, quantity = 1),
            CartItem(brand = "", value = 0.0, image = "",
                vendor = "", payable = 30.0, totalPayable = 30.0, quantity = 1)
        )
        val expectedTotal = 60.0

        assertThat(cartItems.calculateTotal())
            .isEqualTo(expectedTotal)
    }

    @Test
    fun toPercentage() {
        val discount = 90.0
        val expectedPercentage = 10

        assertThat(discount.toPercentage())
            .isEqualTo(expectedPercentage)
    }

    @Test
    fun roundToTwoDecimal() {
        val doubleValue = 50.4375
        val decimalPlaceCount = doubleValue.roundToTwoDecimal()
            .toString().split(".")
            .last().count()

        assertThat(decimalPlaceCount).isEqualTo(2)
    }
}