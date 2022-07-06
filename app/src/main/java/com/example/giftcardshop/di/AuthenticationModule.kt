package com.example.giftcardshop.di

import com.example.giftcardshop.data.data_repository.AuthRepositoryImpl
import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.data.fake_apis.FakeAuthenticationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {
    @Singleton
    @Provides
    fun provideAuthenticator(): FakeAuthenticationApi {
        return FakeAuthenticationApi()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FakeAuthenticationApi): AuthenticationRepository {
        return AuthRepositoryImpl(auth)
    }
}