package com.renju.albums.domain.repository

import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    suspend fun getMusicList(): Flow<Resource<List<MusicDetails>>>
    suspend fun refreshMusicDetails(): Flow<Resource<List<MusicDetails>>>
}