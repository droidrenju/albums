package com.renju.albums.data.entity

import com.google.gson.annotations.SerializedName


data class Entry(
    @SerializedName("im:name")
    val imName: ImName,
    @SerializedName("im:image")
    val imImage: List<ImImage> = emptyList(),
    @SerializedName("im:itemCount")
    val imItemCount: ImItemCount,
    @SerializedName("im:price")
    val imPrice: ImPrice,
    @SerializedName("im:contentType")
    val imContentType: ImContentType,
    @SerializedName("title")
    val musicTitle: MusicTitle,
    @SerializedName("link")
    val musicLink: MusicLink,
    val category: Category,
    @SerializedName("im:releaseDate")
    val imReleaseDate: ImReleaseDate,
    @SerializedName("im:artist")
    val imArtist: ImArtist
)
