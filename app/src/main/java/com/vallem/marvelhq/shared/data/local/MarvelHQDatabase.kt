package com.vallem.marvelhq.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vallem.marvelhq.shared.data.local.entity.ComicEntity

@Database(entities = [ComicEntity::class], version = 1)
abstract class MarvelHQDatabase : RoomDatabase() {
    abstract val favoriteComicsDao: FavoriteComicsDao
}