package com.navico.compose.hilt

import com.navico.compose.retrofit.MarisApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NavicoHiltModule {

    @Provides
    fun provideMarisApiService(): MarisApiService = MarisApiService.Factory.create()
}