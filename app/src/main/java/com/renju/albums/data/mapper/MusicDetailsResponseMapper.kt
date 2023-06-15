package com.renju.albums.data.mapper

import com.renju.albums.domain.model.MusicDetails
import com.renju.albums.data.entity.MusicEntity
import com.renju.albums.data.local.MusicLocalEntity
import javax.inject.Inject

class MusicDetailsResponseMapper @Inject constructor() {

    fun mapToMusicDetailsFromNetwork(musicEntity: MusicEntity?): List<MusicDetails> {
        return musicEntity?.feed?.entry!!.map {
            MusicDetails(
                it.imName.label,
                it.imImage[2].label,
                it.musicTitle.label,
                it.imPrice.label,
                it.imArtist.label,
                it.musicLink.attributes.href
            )
        }
    }

    fun mapToMusicDetailsFromLocal(musicLocalEntityList: List<MusicLocalEntity>) =
        musicLocalEntityList.map {
            MusicDetails(
                it.musicName,
                it.musicImageUrl,
                it.musicTitle,
                it.musicPriceLabel,
                it.artistLabel,
                it.musicLink
            )
        }

    fun mapToLocalEntityFromMusicDetails(musicDetailsList: List<MusicDetails>) =
        musicDetailsList.map {
            MusicLocalEntity(
                it.musicName,
                it.musicImageUrl,
                it.musicTitle,
                it.musicPriceLabel,
                it.artistLabel,
                it.musicLink ?: ""
            )
        }
}