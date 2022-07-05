package com.example.giftcardshop.di

import com.example.giftcardshop.data.data_repository.LocalAuthRepositoryImpl
import com.example.giftcardshop.domain.domain_repository.AuthenticationRepository
import com.example.giftcardshop.shared.FakeAuthenticator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    fun provideAuthenticator(): FakeAuthenticator {
        return FakeAuthenticator()
    }

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FakeAuthenticator): AuthenticationRepository {
        return LocalAuthRepositoryImpl(auth)
    }
}