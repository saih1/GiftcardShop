package com.example.giftcardshop.shared

import com.example.giftcardshop.data.network.dto.Denomination
import com.example.giftcardshop.data.network.dto.GiftcardDto
import com.example.giftcardshop.domain.model.CartItem
import com.example.giftcardshop.domain.model.Giftcard
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilitiesTest {

    @Test
    fun `toGiftcard function, transforms GiftcardDto to Giftcard`() {
        val giftcardDto = GiftcardDto(
            brand = "brand",
            cardTypeStatus = "brand",
            denominations = listOf(Denomination(
                10.0,
                "AUD",
                "stock"
            )),
            disclaimer = "disclaimer",
            discount = 20.0,
            id = "id",
            image = "imageUrl",
            importantContent = "important",
            position = 0,
            terms = "terms",
            vendor = "vendor")
        val expectedGiftcard = Giftcard(
            image = "imageUrl",
            brand = "brand",
            discount = 20.0,
            terms = "terms",
            denominations = listOf(Denomination(
                10.0,
                "AUD",
                "stock"
            )),
            vendor = "vendor")

        assertThat(giftcardDto.toGiftcard()).isEqualTo(expectedGiftcard)
    }

    @Test
    fun `toCartItem function, transforms GiftCard to CartItem`() {
        val selectedValue = 10.0
        val giftCard = Giftcard(
            image = "image",
            brand = "brand",
            discount = 0.0,
            terms = "terms",
            denominations = listOf(Denomination(
                10.0,
                "AUD",
                "stock"
            )),
            vendor = "vendor")
        val expectedCartItem = CartItem(
            brand = "brand",
            value = 10.0,
            image = "image",
            vendor = "vendor"
        )

        assertThat(giftCard.toCartItem(selectedValue)).isEqualTo(expectedCartItem)
    }

    @Test
    fun `calculateTotal function, returns total value from list of cart items`() {
        val cartItemList = listOf<CartItem>(
            CartItem(id = 0, brand = "", value = 10.0, image = "", vendor = ""),
            CartItem(id = 0, brand = "", value = 10.0, image = "", vendor = ""),
            CartItem(id = 0, brand = "", value = 10.0, image = "", vendor = ""),
            CartItem(id = 0, brand = "", value = 10.0, image = "", vendor = "")
        )
        val expectedTotal = 40.0

        assertThat(cartItemList.calculateTotal()).isEqualTo(expectedTotal)
    }
}