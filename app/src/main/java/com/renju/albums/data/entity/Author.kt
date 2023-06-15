package com.renju.albums.data.entity

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName( "name")
    val name: AuthorName,
    @SerializedName("uri")
    val uri: AuthorUri
)
