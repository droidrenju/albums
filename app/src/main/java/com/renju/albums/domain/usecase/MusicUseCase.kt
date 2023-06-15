package com.renju.albums.domain.usecase

import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.repository.MusicRepository
import com.renju.albums.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend fun getMusicDetails(): Flow<Resource<List<MusicDetails>>> {
        return musicRepository.getMusicList()
    }

    suspend fun refreshMusicDetails(): Flow<Resource<List<MusicDetails>>> {
        return musicRepository.refreshMusicDetails()
    }
}