package com.example.giftcardshop.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: GiftcardDispatchers)

enum class GiftcardDispatchers {
    Default,
    IO,
}