package com.renju.albums.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MusicDao {

    @Query("SELECT * FROM music")
    fun getMusicList(): List<MusicLocalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicDetails(musicLocalEntityList: List<MusicLocalEntity>)

}