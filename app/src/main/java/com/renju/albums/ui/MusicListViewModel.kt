package com.renju.albums.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.domain.usecase.MusicUseCase
import com.renju.albums.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val musicUseCase: MusicUseCase
) : ViewModel() {

    internal var state by mutableStateOf(MusicListState())
    internal var musicList: List<MusicDetails> = emptyList()
    internal var refreshing by mutableStateOf(false)

    init {
        fetchMusicList()
    }

    @VisibleForTesting
    internal fun fetchMusicList() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            musicUseCase.getMusicDetails()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    state = state.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            musicList = it.data ?: emptyList()
                            state = state.copy(
                                musicDetailsList = it.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                musicDetailsList = null,
                                isLoading = false,
                                error = it.message
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                    }
                }
        }
    }


    fun refreshData() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshing = true
            musicUseCase.refreshMusicDetails()
                .collect {
                    if (it is Resource.Success) {
                        state = state.copy(
                            musicDetailsList = it.data,
                            error = null
                        )
                    } else if (it is Resource.Error) {
                        state = state.copy(
                            musicDetailsList = null,
                            isLoading = false,
                            error = it.message
                        )
                    }
                }
            refreshing = false
        }
    }


    fun filterList(searchQuery: String) {
        val filteredList = musicList.asSequence().filter { musicDetail ->
            musicDetail.musicName.contains(searchQuery, ignoreCase = true)
        }

        state = state.copy(
            musicDetailsList = filteredList.toList(),
            isLoading = false,
            error = null
        )
    }
}