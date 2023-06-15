package com.renju.albums.data.entity

import com.google.gson.annotations.SerializedName

data class Attributes(
    val height: String? = "",
    val rel: String? = "",
    val type: String? ="",
    val href: String? ="",
    val amount: String? = "",
    val currency: String? ="",
    val term: String? = "",
    val label: String? = "",
    @SerializedName("im:id")
    val imId: String? = "",
    val scheme: String? ="",
)
