package com.renju.albums.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MusicLocalEntity::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao
}