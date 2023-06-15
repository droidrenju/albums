package com.renju.albums.domain.model

data class MusicDetails(
    val musicName: String,
    val musicImageUrl: String = "",
    val musicTitle: String = "",
    val musicPriceLabel: String = "",
    val artistLabel: String = "",
    val musicLink: String? = ""
)
