package com.example.giftcardshop.di

import com.example.giftcardshop.domain.domain_repository.GiftcardRepository
import com.example.giftcardshop.data.data_repository.GiftcardRepositoryImpl
import com.example.giftcardshop.data.network.GiftcardApi
import com.example.giftcardshop.shared.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGiftcardApi(): GiftcardApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(Moshi
                            .Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                    )
            )
            .build()
            .create(GiftcardApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGiftcardRepository(giftcardApi: GiftcardApi): GiftcardRepository {
         return GiftcardRepositoryImpl(giftcardApi)
    }
}