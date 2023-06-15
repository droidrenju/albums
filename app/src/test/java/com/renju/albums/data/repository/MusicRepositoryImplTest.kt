package com.renju.albums.data.repository

import com.renju.albums.data.entity.*
import com.renju.albums.data.local.MusicDao
import com.renju.albums.data.local.MusicLocalEntity
import com.renju.albums.data.mapper.MusicDetailsResponseMapper
import com.renju.albums.data.remote.ItunesService
import com.renju.albums.data.testutils.CoroutineTestRule
import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verifyNoInteractions
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MusicRepositoryImplTest {

    @Mock
    private lateinit var itunesService: ItunesService

    @Mock
    private lateinit var mapper: MusicDetailsResponseMapper


    @Mock
    private lateinit var musicDao: MusicDao

    @Mock
    private lateinit var response: Response<MusicEntity>

    @Mock
    private lateinit var musicEntity: MusicEntity

    private lateinit var repository: MusicRepositoryImpl

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = MusicRepositoryImpl(itunesService, mapper, musicDao)

    }



    @Test
    fun `getMusicList emits loading and success when data is not available locally`() =
        coroutineTestRule.runBlockingTest {
            val cachedData = emptyList<MusicLocalEntity>()
            `when`(musicDao.getMusicList()).thenReturn(cachedData)

            `when`(itunesService.getTopMusicAlbums()).thenReturn(response)
            `when`(response.isSuccessful).thenReturn(true)
            `when`(response.body()).thenReturn(musicEntity)


            val mappedData = listOf(
                MusicDetails("music1", "url1", "title1", "price1", "artist1"),
                MusicDetails("music2", "url2", "title2", "price2", "artist2"),
                MusicDetails("music3", "url2", "title3", "price3", "artist3"),
            )
            `when`(mapper.mapToMusicDetailsFromNetwork(musicEntity)).thenReturn(mappedData)

            val flow = repository.getMusicList().toList()

            assertEquals(2, flow.size)
            assertTrue(flow[0] is Resource.Loading)
            assertTrue(flow[1] is Resource.Success)
            assertEquals(mappedData, (flow[1] as Resource.Success<List<MusicDetails>>).data)

        }

    @Test
    fun `getMusicList emits loading and error when error response received`() =
        coroutineTestRule.runBlockingTest {

            `when`(itunesService.getTopMusicAlbums()).thenReturn(response)
            `when`(response.isSuccessful).thenReturn(false)
            `when`(response.code()).thenReturn(500)


            val flow = repository.getMusicList().toList()

            assertEquals(2, flow.size)
            assertTrue(flow[0] is Resource.Loading)
            assertTrue(flow[1] is Resource.Error)
            assertEquals("Request failed with code: 500", (flow[1] as Resource.Error).message)

        }

    @Test
    fun `getMusicList emits loading and success when data is available locally`() =
        coroutineTestRule.runBlockingTest {
            val cachedData =
                listOf(
                    MusicLocalEntity("music1", "url1", "title1", "price1", "artist1"),
                    MusicLocalEntity("music2", "url2", "title2", "price2", "artist2"),
                    MusicLocalEntity("music3", "url2", "title3", "price3", "artist3")
                )
            `when`(musicDao.getMusicList()).thenReturn(cachedData)

            val mappedData = listOf(
                MusicDetails("music1", "url1", "title1", "price1", "artist1"),
                MusicDetails("music2", "url2", "title2", "price2", "artist2"),
                MusicDetails("music3", "url2", "title3", "price3", "artist3"),
            )
            `when`(mapper.mapToMusicDetailsFromLocal(cachedData)).thenReturn(mappedData)


            val flow = repository.getMusicList().toList()

            assertEquals(2, flow.size)
            assertTrue(flow[0] is Resource.Loading)
            assertTrue(flow[1] is Resource.Success)
            assertEquals(mappedData, (flow[1] as Resource.Success<List<MusicDetails>>).data)
            verifyNoInteractions(itunesService)
        }


    @Test
    fun `refreshMusicList emits data when network response is success`() =
        coroutineTestRule.runBlockingTest {

            `when`(itunesService.getTopMusicAlbums()).thenReturn(response)
            `when`(response.isSuccessful).thenReturn(true)
            `when`(response.body()).thenReturn(musicEntity)


            val mappedData = listOf(
                MusicDetails("music1", "url1", "title1", "price1", "artist1"),
                MusicDetails("music2", "url2", "title2", "price2", "artist2"),
                MusicDetails("music3", "url2", "title3", "price3", "artist3"),
            )
            `when`(mapper.mapToMusicDetailsFromNetwork(musicEntity)).thenReturn(mappedData)

            val flow = repository.refreshMusicDetails().toList()

            assertEquals(1, flow.size)
            assertTrue(flow[0] is Resource.Success)
            assertEquals(mappedData, (flow[0] as Resource.Success<List<MusicDetails>>).data)
        }

    @Test
    fun `refreshMusicList should not emit data when network response is failure`() =
        coroutineTestRule.runBlockingTest {
            `when`(itunesService.getTopMusicAlbums()).thenReturn(response)
            `when`(response.isSuccessful).thenReturn(false)

            val flow = repository.refreshMusicDetails().toList()

            assertEquals(1, flow.size)
            assertTrue(flow[0] is Resource.Error)
            verifyNoInteractions(mapper)
        }
}