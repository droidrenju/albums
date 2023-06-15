package com.renju.albums.data.entity

import com.google.gson.annotations.SerializedName


data class Feed(
    val author: Author? = null,
    @SerializedName("updated")
    val updated: LastUpdate? = null,
    val entry: List<Entry>? = emptyList(),
    val title: Title? = null)
