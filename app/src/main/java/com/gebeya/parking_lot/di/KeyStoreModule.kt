package com.gebeya.parking_lot.di

import android.app.Application
import com.gebeya.parking_lot.data.keystore.KeyStoreAccessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object KeyStoreModule {


    @Provides
    @Singleton
    fun providesKeyStoreAccessor(app: Application): KeyStoreAccessor {
        return KeyStoreAccessor(app)
    }
}