package com.renju.albums.ui

import com.renju.albums.domain.model.MusicDetails


data class MusicListState(
    val musicDetailsList: List<MusicDetails>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)