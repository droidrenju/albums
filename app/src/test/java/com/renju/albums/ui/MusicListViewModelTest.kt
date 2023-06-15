package com.renju.albums.ui

import com.google.common.truth.Truth.*
import com.renju.albums.data.testutils.CoroutineTestRule
import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.usecase.MusicUseCase
import com.renju.albums.domain.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MusicListViewModelTest {

    @Mock
    private lateinit var musicUseCase: MusicUseCase

    private lateinit var viewModel: MusicListViewModel


    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MusicListViewModel(musicUseCase)
    }

    @Before
    fun tearDown() {
        viewModel.musicList = emptyList()
    }


    @Test
    fun `fetchMusicList should update state when musicUseCase returns data`() =
        coroutineTestRule.runBlockingTest {
            val musicDetailsList = listOf(
                MusicDetails("Song 1"),
                MusicDetails("Song 2")
            )
            val result = Resource.Success(musicDetailsList)
            `when`(musicUseCase.getMusicDetails()).thenReturn(flowOf(result))

            viewModel.fetchMusicList()

            coroutineTestRule.testDispatcher.scheduler.advanceUntilIdle()

            assertThat(viewModel.state.isLoading).isFalse()
            assertThat(viewModel.state.error).isNull()
            assertThat(viewModel.state.musicDetailsList).isEqualTo(musicDetailsList)
        }

    @Test
    fun `filterList should update state with filtered music list`() {
        val initialMusicList = listOf(
            MusicDetails("Song 1"),
            MusicDetails("Song 2"),
            MusicDetails("Another Song")
        )
        viewModel.musicList = initialMusicList

        viewModel.filterList("Another")

        assertThat(viewModel.state.isLoading).isFalse()
        assertThat(viewModel.state.error).isNull()
        assertThat(viewModel.state.musicDetailsList).containsExactly(
            MusicDetails("Another Song")
        )
    }
}

