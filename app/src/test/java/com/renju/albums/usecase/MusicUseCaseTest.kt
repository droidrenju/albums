package com.renju.albums.usecase

import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.repository.MusicRepository
import com.renju.albums.domain.util.Resource
import com.renju.albums.domain.util.Resource.Success
import com.google.common.truth.Truth.*
import com.renju.albums.domain.usecase.MusicUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MusicUseCaseTest {

    @Mock
    private lateinit var musicRepository: MusicRepository

    private lateinit var musicUseCase: MusicUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        musicUseCase = MusicUseCase(musicRepository)
    }

    @Test
    fun `getMusicDetails returns Flow of Resource with list of MusicDetails`() = runBlocking {
        val musicList = listOf(
            MusicDetails("music1", "url1", "title1", "price1", "artist1"),
            MusicDetails("music2", "url2", "title2", "price2", "artist2"),
            MusicDetails("music3", "url3", "title3", "price3", "artist3")
        )
        val flow = flowOf(Success(musicList))
        `when`(musicRepository.getMusicList()).thenReturn(flow)

        val result = musicUseCase.getMusicDetails().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Success)
        assertEquals(musicList, (result[0] as Success<List<MusicDetails>>).data)
    }

    @Test
    fun `getMusicDetails returns Flow of Resource with error`() = runBlocking {
        val error = "Request failed"
        `when`(musicRepository.getMusicList()).thenReturn(flowOf(Resource.Error(error)))

        val result = musicUseCase.getMusicDetails().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals(error, (result[0] as Resource.Error).message)
    }

    @Test
    fun `refreshMusicDetails returns Flow of Resource with list of MusicDetails`() = runBlocking {
        val musicList = listOf(
            MusicDetails("music1", "url1", "title1", "price1", "artist1"),
            MusicDetails("music2", "url2", "title2", "price2", "artist2"),
            MusicDetails("music3", "url3", "title3", "price3", "artist3")
        )
        val flow = flowOf(Success(musicList))
        `when`(musicRepository.refreshMusicDetails()).thenReturn(flow)

        val result = musicUseCase.refreshMusicDetails().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Success)
        assertEquals(musicList, (result[0] as Success<List<MusicDetails>>).data)
    }

    @Test
    fun `refreshMusicDetails returns Flow of Resource with error`() = runBlocking {
        val error = "Request failed"
        `when`(musicRepository.refreshMusicDetails()).thenReturn(flowOf(Resource.Error(error)))

        val result = musicUseCase.refreshMusicDetails().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals(error, (result[0] as Resource.Error).message)
    }
}
