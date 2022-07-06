package com.example.giftcardshop.di

import com.example.giftcardshop.data.data_repository.CheckoutRepositoryImpl
import com.example.giftcardshop.data.fake_apis.FakeCheckoutApi
import com.example.giftcardshop.domain.domain_repository.CheckoutRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckoutModule {
    @Singleton
    @Provides
    fun provideCheckoutApi(): FakeCheckoutApi {
        return FakeCheckoutApi()
    }

    @Singleton
    @Provides
    fun provideCheckoutRepository(checkoutApi: FakeCheckoutApi): CheckoutRepository {
        return CheckoutRepositoryImpl(checkoutApi)
    }
}