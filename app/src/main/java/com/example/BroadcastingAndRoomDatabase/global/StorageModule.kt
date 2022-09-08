package com.example.BroadcastingAndRoomDatabase.global

import com.example.BroadcastingAndRoomDatabase.PreferenceImpl
import com.example.BroadcastingAndRoomDatabase.PreferenceStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {
    @Binds
    abstract fun bindsPreferenceStorage(preferenceStorageImpl: PreferenceImpl): PreferenceStorage

}