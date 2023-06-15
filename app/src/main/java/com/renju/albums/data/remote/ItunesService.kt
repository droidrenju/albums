package com.renju.albums.data.remote

import com.renju.albums.data.entity.MusicEntity
import retrofit2.Response
import retrofit2.http.GET

interface ItunesService {
    @GET("/us/rss/topalbums/limit=100/json")
    suspend fun getTopMusicAlbums(): Response<MusicEntity>
}