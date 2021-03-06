package com.example.giftcardshop.di

import android.content.Context
import androidx.room.Room
import com.example.giftcardshop.data.data_repository.CartRepositoryImpl
import com.example.giftcardshop.data.data_repository.PersistenceRepositoryImpl
import com.example.giftcardshop.data.local.AuthDataStore
import com.example.giftcardshop.data.local.CartItemDao
import com.example.giftcardshop.data.local.CartItemDatabase
import com.example.giftcardshop.domain.domain_repository.CartRepository
import com.example.giftcardshop.domain.domain_repository.PersistenceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CartItemDatabase {
        return Room.databaseBuilder(
            context,
            CartItemDatabase::class.java,
            "cart_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideCartItemDao(database: CartItemDatabase): CartItemDao {
        return database.cartItemDao()
    }

    @Provides
    @Singleton
    fun provideCartItemRepository(cartItemDao: CartItemDao): CartRepository {
        return CartRepositoryImpl(cartItemDao)
    }

    @Singleton
    @Provides
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStore(context)
    }

    @Provides
    @Singleton
    fun providePersistenceRepository(authDataStore: AuthDataStore): PersistenceRepository {
        return PersistenceRepositoryImpl(authDataStore)
    }
}