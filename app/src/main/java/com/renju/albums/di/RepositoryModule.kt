package com.renju.albums.di

import com.renju.albums.data.local.MusicDao
import com.renju.albums.data.mapper.MusicDetailsResponseMapper
import com.renju.albums.data.remote.ItunesService
import com.renju.albums.data.repository.MusicRepositoryImpl
import com.renju.albums.domain.repository.MusicRepository
import com.renju.albums.domain.usecase.MusicUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideMainRepository(
        itunesService: ItunesService,
        mapper: MusicDetailsResponseMapper,
        musicDao: MusicDao
    ): MusicRepository {
        return MusicRepositoryImpl(itunesService, mapper, musicDao)
    }

    @Provides
    @ViewModelScoped
    fun providesMusicUseCase(repository: MusicRepository): MusicUseCase =
        MusicUseCase(repository)
}
