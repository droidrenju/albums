package com.renju.albums.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class MusicLocalEntity(
    val musicName: String,
    val musicImageUrl: String = "",
    val musicTitle: String = "",
    val musicPriceLabel: String = "",
    val artistLabel: String = "",
    val musicLink: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
