package com.renju.albums.data.repository

import com.renju.albums.data.local.MusicDao
import com.renju.albums.data.mapper.MusicDetailsResponseMapper
import com.renju.albums.data.remote.ItunesService
import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.repository.MusicRepository
import com.renju.albums.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val itunesService: ItunesService,
    private val mapper: MusicDetailsResponseMapper,
    private val musicDao: MusicDao
) : MusicRepository {
    override suspend fun getMusicList() =
        flow {
            emit(Resource.Loading())
            val cachedData = musicDao.getMusicList()
            if (cachedData.isNotEmpty()) {
                emit(Resource.Success(data = mapper.mapToMusicDetailsFromLocal(cachedData)))
            } else {
                fetchMusicDetailsFromNetwork()
            }
        }.catch {
            emit(Resource.Error(it.message ?: "An unknown error occurred."))
        }

    private suspend fun FlowCollector<Resource<List<MusicDetails>>>.fetchMusicDetailsFromNetwork() {
        val networkResponse = itunesService.getTopMusicAlbums()
        if (networkResponse.isSuccessful) {
            val mappedData = mapper.mapToMusicDetailsFromNetwork(
                networkResponse.body()
            )
            val dataToInsert = mapper.mapToLocalEntityFromMusicDetails(mappedData)
            musicDao.insertMusicDetails(dataToInsert)

            emit(Resource.Success(mappedData))
        } else {
            emit(Resource.Error("Request failed with code: ${networkResponse.code()}"))
        }
    }

    override suspend fun refreshMusicDetails(): Flow<Resource<List<MusicDetails>>> =
        flow { fetchMusicDetailsFromNetwork() }.catch {
            emit(
                Resource.Error(
                    it.message ?: "An unknown error occurred."
                )
            )
        }

}